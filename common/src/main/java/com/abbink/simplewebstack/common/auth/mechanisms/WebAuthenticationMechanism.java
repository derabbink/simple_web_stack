package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;

import com.abbink.simplewebstack.common.error.UnauthorizedException;
import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.spi.container.ContainerRequest;

/**
 * This is a combination authentication mechanism that understands running sessions and remember-me cookies.
 * The mechanism always tries to authenticate both, which is needed if the subject wants to log out: Only if principals
 * from both authentication methods exist in the subject can the logout process invalidate both.
 */
public class WebAuthenticationMechanism extends AuthenticationMechanism {
	
	public static final String REMEMBER_ME_COOKIE = "acct";
	public static final String SESSION_CREDENTIALS = WebAuthenticationMechanism.class.getName() + "_SESSION_CREDENTIALS";
	
	private WebSecurityManager securityManager;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	@Inject
	public WebAuthenticationMechanism(
		WebSecurityManager securityManager,
		@Assisted  HttpServletRequest servletRequest,
		@Assisted  HttpServletResponse servletResponse
	) {
		this.securityManager = securityManager;
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
	}
	
	/**
	 * Look for active session, or fall back to remember-me cookie if there is none.
	 * In case of the latter, a new session will created.
	 * @param request
	 * @return
	 */
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		return doFilter(
			request,
			securityManager,
			servletRequest,
			servletResponse
		);
	}
	
	/**
	 * Reusable logic fore composing AuthenticationMechanisms
	 */
	static ContainerRequest doFilter(
		ContainerRequest request,
		WebSecurityManager securityManager,
		HttpServletRequest servletRequest,
		HttpServletResponse servletResponse
	) {
		WebSubject subject = new WebSubject.Builder(securityManager, servletRequest, servletResponse).buildWebSubject();
		Session session = subject.getSession(false);
		if (session != null) {
			session.touch();
		}
		
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			throw new UnauthorizedException(200, "Not logged in");
		}
		
		// Store subject in request.
		request.getProperties().put(SUBJECT_KEY, subject);
		
		return request;
	}
}
