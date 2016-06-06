package com.abbink.simplewebstack.common.auth.mechanisms;

import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public abstract class AuthenticationMechanism implements ResourceFilter, ContainerRequestFilter {
	
	/**
	 * Key used to store Subject instance in a ContainerRequest's properties
	 */
	public static final String SUBJECT_KEY = AuthenticationMechanism.class.getName() + "_SUBJECT_KEY";
	
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
