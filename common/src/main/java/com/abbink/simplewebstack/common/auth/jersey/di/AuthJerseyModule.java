package com.abbink.simplewebstack.common.auth.jersey.di;

import java.util.Map;

import com.abbink.simplewebstack.common.auth.jersey.AuthResourceFilterFactory;
import com.abbink.simplewebstack.common.auth.jersey.SubjectInjectableProvider;
import com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

public class AuthJerseyModule extends AbstractModule {
	@Override
	protected void configure() {
		requireBinding(Key.get(new TypeLiteral<Map<Class<? extends AuthenticationMechanism>, AuthenticationMechanism.Factory>>(){}));
		
		// add @Auth resource method interceptor to jersey
		bind(AuthResourceFilterFactory.class);
		
		// Make Subject a @Context injectable for Jersey
		bind(SubjectInjectableProvider.class);
	}
}