package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.mgt.WebSecurityManager;

import com.abbink.simplewebstack.common.error.UnauthorizedException;
import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.spi.container.ContainerRequest;

/**
 * An AuthenticationMechanism that attempts to authenticate using the FormAuthenticationMechanism,
 * and falls back to using the AnonymousAuthenticationMechanism
 */
public class OptionalWebAuthenticationMechanism extends AuthenticationMechanism {
	
	private WebSecurityManager securityManager;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	@Inject
	public OptionalWebAuthenticationMechanism(
		WebSecurityManager securityManager,
		@Assisted  HttpServletRequest servletRequest,
		@Assisted  HttpServletResponse servletResponse
	) {
		this.securityManager = securityManager;
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
	}
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		return doFilter(
			request,
			securityManager,
			servletRequest,
			servletResponse
		);
	}
	
	static ContainerRequest doFilter(
		ContainerRequest request,
		WebSecurityManager securityManager,
		HttpServletRequest servletRequest,
		HttpServletResponse servletResponse
	) {
		try {
			return WebAuthenticationMechanism.doFilter(
				request,
				securityManager,
				servletRequest,
				servletResponse
			);
		} catch (UnauthorizedException e) {}
		
		return AnonymousAuthenticationMechanism.doFilter(
			request,
			securityManager,
			servletRequest,
			servletResponse
		);
	}
	
	
}
