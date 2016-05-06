package com.abbink.simplewebstack.common.auth.di;

import com.abbink.simplewebstack.common.aop.Auth;
import com.abbink.simplewebstack.common.aop.AuthInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class AuthInterceptorModule extends AbstractModule {
	protected void configure() {
		bindInterceptor(
			Matchers.annotatedWith(Auth.class),
			Matchers.any(),
			new AuthInterceptor()
		);
		bindInterceptor(
			Matchers.any(),
			Matchers.annotatedWith(Auth.class), 
			new AuthInterceptor()
		);
	}
}
