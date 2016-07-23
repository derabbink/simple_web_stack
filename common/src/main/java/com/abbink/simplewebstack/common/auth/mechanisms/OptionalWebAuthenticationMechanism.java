package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abbink.simplewebstack.common.auth.shiro.WebSecurityManager;
import com.abbink.simplewebstack.common.error.UnauthorizedException;
import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.spi.container.ContainerRequest;

/**
 * An AuthenticationMechanism that attempts to authenticate using the FormAuthenticationMechanism,
 * and falls back to using the AnonymousAuthenticationMechanism
 */
public class OptionalWebAuthenticationMechanism extends AuthenticationMechanism {
	
	private WebSecurityManager formSecurityManager;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	@Inject
	public OptionalWebAuthenticationMechanism(
		WebSecurityManager formSecurityManager,
		@Assisted  HttpServletRequest servletRequest,
		@Assisted  HttpServletResponse servletResponse
	) {
		this.formSecurityManager = formSecurityManager;
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
	}
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		return doFilter(
			request,
			formSecurityManager,
			servletRequest,
			servletResponse
		);
	}
	
	static ContainerRequest doFilter(
		ContainerRequest request,
		WebSecurityManager formSecurityManager,
		HttpServletRequest servletRequest,
		HttpServletResponse servletResponse
	) {
		try {
			return WebAuthenticationMechanism.doFilter(
				request,
				formSecurityManager,
				servletRequest,
				servletResponse
			);
		} catch (UnauthorizedException e) {}
		
		return AnonymousAuthenticationMechanism.doFilter(request);
	}
	
	
}
