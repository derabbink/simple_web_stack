package com.abbink.simplewebstack.common.auth.jersey.di;

import com.abbink.simplewebstack.common.auth.jersey.AuthResourceFilterFactory;
import com.abbink.simplewebstack.common.auth.jersey.SubjectInjectableProvider;
import com.google.inject.AbstractModule;

public class AuthJerseyModule extends AbstractModule {
	@Override
	protected void configure() {
		// add @Auth resource method interceptor to jersey
		bind(AuthResourceFilterFactory.class);
		
		// Make Subject a @Context injectable for Jersey
		bind(SubjectInjectableProvider.class);
	}
}