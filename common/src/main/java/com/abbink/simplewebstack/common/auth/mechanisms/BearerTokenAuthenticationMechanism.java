package com.abbink.simplewebstack.common.auth.mechanisms;

import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.HEAD;
import static javax.ws.rs.HttpMethod.POST;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

import com.abbink.simplewebstack.common.auth.shiro.BearerTokenAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.BearerTokenSecurityManager;
import com.abbink.simplewebstack.common.errors.UnauthorizedException;
import com.sun.jersey.spi.container.ContainerRequest;

public class BearerTokenAuthenticationMechanism extends AuthenticationMechanism {
	
	public static final String TOKEN_NAME = "access_token";
	
	private BearerTokenSecurityManager securityManager;
	
	@Inject
	public BearerTokenAuthenticationMechanism(BearerTokenSecurityManager securityManager) {
		this.securityManager = securityManager;
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
		
		DefaultWebSubjectContext context = new DefaultWebSubjectContext();
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
