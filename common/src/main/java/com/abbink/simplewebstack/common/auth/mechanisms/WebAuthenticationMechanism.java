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
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

import com.abbink.simplewebstack.common.auth.shiro.WebSecurityManager;
import com.abbink.simplewebstack.common.auth.shiro.authtokens.RememberMeAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.authtokens.SessionAuthenticationToken;
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
		// perform both session & remember me authentication processes, and turn them into a single Subject instance,
		// which is then stored in the request properties
		Subject sessionSubject = null;
		Subject rememberMeSubject = null;
		RuntimeException sessionError = null;
		RuntimeException rememberError = null;
		try {
			sessionSubject = getSessionSubject(request, securityManager, servletRequest, servletResponse);
		} catch (AuthenticationException e) {
			sessionError = e;
		} catch (UnauthorizedException e) {
			sessionError = e;
		}
		try {
			rememberMeSubject = getRememberMeSubject(request, securityManager, servletRequest, servletResponse);
		} catch (AuthenticationException e) {
			rememberError = e;
		} catch (UnauthorizedException e) {
			rememberError = e;
		}
		
		Subject subject = null;
		if (sessionSubject == null && rememberMeSubject != null) {
			subject = rememberMeSubject;
		} else if (sessionSubject != null && rememberMeSubject == null) {
			subject = sessionSubject;
		} else if (sessionSubject != null && rememberMeSubject != null) {
			if (!sessionSubject.getPrincipal().equals(rememberMeSubject.getPrincipal())) {
				sessionSubject.logout();
				rememberMeSubject.logout();
			}
			subject = securityManager.unifySubjects(sessionSubject, rememberMeSubject);
		} else if (sessionSubject == null && rememberMeSubject == null) {
			if (sessionError != null) {
				throw sessionError;
			}
			if (rememberError != null) {
				throw rememberError;
			}
			throw new UnauthorizedException(200, "Not logged in");
		}
		// Store subject in request. Don't bind Subject (and SecurityManager) to ThreadContext.
		request.getProperties().put(SUBJECT_KEY, subject);
		
		return request;
	}
	
	private static Subject getSessionSubject(
		ContainerRequest request,
		WebSecurityManager securityManager,
		HttpServletRequest servletRequest,
		HttpServletResponse servletResponse
	) {
		WebSessionContext sessionContext = new DefaultWebSessionContext();
		sessionContext.setServletRequest(servletRequest);
		sessionContext.setServletResponse(servletResponse);
		Session session = securityManager.getSession((SessionKey)sessionContext);
		
		if (session == null) {
			return null;
		}
		Object rawCredentials = session.getAttribute(SESSION_CREDENTIALS);
		String credentials = null;
		if (rawCredentials != null && rawCredentials instanceof String) {
			credentials = (String)rawCredentials;
		}
		if (credentials == null) {
			session.stop();
			throw new UnauthorizedException(200, "Not signed in.");
		}
		
		String id = session.getId().toString();
		AuthenticationToken token = new SessionAuthenticationToken(id, credentials);
		return getSubjectFromAuthToken(token, request, securityManager, servletRequest, servletResponse, session);
	}
	
	private static Subject getRememberMeSubject(
		ContainerRequest request,
		WebSecurityManager securityManager,
		HttpServletRequest servletRequest,
		HttpServletResponse servletResponse
	) {
		Cookie rememberMeCookie = request.getCookies().get(REMEMBER_ME_COOKIE);
		if (rememberMeCookie == null) {
			throw new UnauthorizedException(200, "Not signed in.");
		}
		
		AuthenticationToken token = new RememberMeAuthenticationToken(rememberMeCookie.getValue());
		return getSubjectFromAuthToken(token, request, securityManager, servletRequest, servletResponse, null);
	}
	
	private static Subject getSubjectFromAuthToken(
		AuthenticationToken token,
		ContainerRequest request,
		WebSecurityManager securityManager,
		HttpServletRequest servletRequest,
		HttpServletResponse servletResponse,
		Session session
	) {
		WebSubjectContext context = new DefaultWebSubjectContext();
		context.setServletRequest(servletRequest);
		context.setServletResponse(servletResponse);
		if (session != null) {
			context.setSession(session);
		}
		context.setSessionCreationEnabled(false); // sessions are created in the UsernamePasswordLoginService
		Subject subject = securityManager.createSubject(context);
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			throw new UnauthorizedException(200, "Invalid session provided.", e);
		}
		return subject;
	}
}
