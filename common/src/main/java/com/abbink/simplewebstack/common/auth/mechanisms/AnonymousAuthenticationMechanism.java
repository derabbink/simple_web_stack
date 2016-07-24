package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;

import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.spi.container.ContainerRequest;

@Slf4j
public class AnonymousAuthenticationMechanism extends AuthenticationMechanism {
	
	private WebSecurityManager securityManager;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	@Inject
	public AnonymousAuthenticationMechanism(
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
		log.info("Applying AnonymousAuthenticationMechanism to {} {}", request.getMethod(), request.getPath());
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
		// don't filter request, but do create an anonymous subject
		WebSubject subject = new WebSubject.Builder(securityManager, servletRequest, servletResponse).buildWebSubject();
		// Store subject in request.
		request.getProperties().put(SUBJECT_KEY, subject);
		
		return request;
	}
}
