package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Cookie;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

import com.abbink.simplewebstack.common.auth.shiro.FormSecurityManager;
import com.abbink.simplewebstack.common.auth.shiro.RememberMeAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.SessionAuthenticationToken;
import com.abbink.simplewebstack.common.errors.UnauthorizedException;
import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.spi.container.ContainerRequest;

public class FormAuthenticationMechanism extends AuthenticationMechanism {
	
	public static final String REMEMBER_ME_COOKIE = "acct";
	public static final String SESSION_CREDENTIALS = FormAuthenticationMechanism.class.getName() + "_SESSION_CREDENTIALS";
	
	private FormSecurityManager securityManager;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	@Inject
	public FormAuthenticationMechanism(
		FormSecurityManager securityManager,
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
		WebSessionContext sessionContext = new DefaultWebSessionContext();
		sessionContext.setServletRequest(servletRequest);
		sessionContext.setServletResponse(servletResponse);
		// get session
		Session session = securityManager.getSession((SessionKey)sessionContext);
		
		AuthenticationToken token = null;
		if (session != null) {
			Object rawCredentials = session.getAttribute(SESSION_CREDENTIALS);
			String credentials = null;
			if (rawCredentials != null && rawCredentials instanceof String) {
				credentials = (String)rawCredentials;
			}
			if (credentials == null) {
				throw new UnauthorizedException(200, "Not signed in.");
			}
			
			String id = session.getId().toString();
			token = new SessionAuthenticationToken(id, credentials);
		} else {
			// get remember-me cookie
			Cookie rememberMeCookie = request.getCookies().get(REMEMBER_ME_COOKIE);
			if (rememberMeCookie == null) {
				throw new UnauthorizedException(200, "Not signed in.");
			}
			
			token = new RememberMeAuthenticationToken(rememberMeCookie.getValue());
		}
		
		DefaultWebSubjectContext context = new DefaultWebSubjectContext();
		context.setServletRequest(servletRequest);
		context.setServletResponse(servletResponse);
		Subject subject = securityManager.createSubject(context);
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			throw new UnauthorizedException(200, "Invalid session provided.", e);
		}
		
		// Store subject in request. Don't bind Subject (and SecurityManager) to ThreadContext.
		request.getProperties().put(SUBJECT_KEY, subject);
		
		return request;
	}
	
}
