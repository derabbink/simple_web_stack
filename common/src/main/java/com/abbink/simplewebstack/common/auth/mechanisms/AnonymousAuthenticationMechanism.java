package com.abbink.simplewebstack.common.auth.mechanisms;

import javax.inject.Singleton;

import lombok.extern.java.Log;

import com.sun.jersey.spi.container.ContainerRequest;

@Singleton
@Log
public class AnonymousAuthenticationMechanism extends AuthenticationMechanism {
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		log.info("Treating request for " + request.getMethod() + " as anonymous.");
		// don't filter the request
		return request;
	}
	
}
