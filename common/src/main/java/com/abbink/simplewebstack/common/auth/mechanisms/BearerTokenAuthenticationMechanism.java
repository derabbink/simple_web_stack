package com.abbink.simplewebstack.common.auth.mechanisms;

import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.HEAD;
import static javax.ws.rs.HttpMethod.POST;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

import com.abbink.simplewebstack.common.auth.shiro.BearerTokenSecurityManager;
import com.abbink.simplewebstack.common.auth.shiro.authtokens.BearerTokenAuthenticationToken;
import com.abbink.simplewebstack.common.error.UnauthorizedException;
import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.spi.container.ContainerRequest;

public class BearerTokenAuthenticationMechanism extends AuthenticationMechanism {
	
	public static final String TOKEN_NAME = "access_token";
	
	private BearerTokenSecurityManager securityManager;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	@Inject
	private BearerTokenAuthenticationMechanism(
		BearerTokenSecurityManager securityManager,
		@Assisted  HttpServletRequest servletRequest,
		@Assisted  HttpServletResponse servletResponse
	) {
		this.securityManager = securityManager;
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
	}
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		String method = request.getMethod();
		String token = null;
		if (GET.equals(method) ||
			DELETE.equals(method) ||
			HEAD.equals(method)
		) {
			token = request.getQueryParameters().getFirst(TOKEN_NAME);
		} else if (POST.equals(method)) {
			token = request.getFormParameters().getFirst(TOKEN_NAME);
		}
		
		WebSubjectContext context = new DefaultWebSubjectContext();
		context.setServletRequest(servletRequest);
		context.setServletResponse(servletResponse);
		Subject subject = securityManager.createSubject(context);
		try {
			subject.login(new BearerTokenAuthenticationToken(token));
		} catch (AuthenticationException e) {
			throw new UnauthorizedException(200, "Invalid access token provided.", e);
		}
		
		// Store subject in request. Don't bind Subject (and SecurityManager) to ThreadContext.
		request.getProperties().put(SUBJECT_KEY, subject);
		
		return request;
	}
}
