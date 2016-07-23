package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;

import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.spi.container.ContainerRequest;

@Log
public class AnonymousAuthenticationMechanism2 extends AuthenticationMechanism {
	
	private WebSecurityManager securityManager;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	
	@Inject
	public AnonymousAuthenticationMechanism2(
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
		// don't filter request, but do create an anonymous subject
		WebSubject subject = new WebSubject.Builder(securityManager, servletRequest, servletResponse).buildWebSubject();
		// Store subject in request.
		request.getProperties().put(SUBJECT_KEY, subject);
		
		return request;
	}
}
