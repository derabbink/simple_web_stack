package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import com.sun.jersey.spi.container.ContainerRequest;

@Slf4j
public class RejectAuthenticationMechanism extends AuthenticationMechanism {
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		return doFilter(request);
	}
	
	/**
	 * Reusable logic fore composing AuthenticationMechanisms
	 */
	static ContainerRequest doFilter(ContainerRequest request) {
		log.info("Applying RejectAuthenticationMechanism to {} {}", request.getMethod(), request.getPath());
		throw new WebApplicationException(Response.Status.UNAUTHORIZED);
	}
}
