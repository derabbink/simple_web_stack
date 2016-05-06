package com.abbink.simplewebstack.common.auth.mechanisms;

import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public abstract class AuthenticationMechanism implements ResourceFilter, ContainerRequestFilter {
	
	@Override
	public final ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public final ContainerResponseFilter getResponseFilter() {
		// don't filter responses
		return null;
	}
	
}
