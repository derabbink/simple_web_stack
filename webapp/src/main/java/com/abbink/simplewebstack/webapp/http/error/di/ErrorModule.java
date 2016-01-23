package com.abbink.simplewebstack.webapp.http.error.di;

import com.abbink.simplewebstack.webapp.http.error.AuthenticationExceptionHandler;
import com.abbink.simplewebstack.webapp.http.error.NotFoundExceptionHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ErrorModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(NotFoundExceptionHandler.class).in(Scopes.SINGLETON);
		bind(AuthenticationExceptionHandler.class).in(Scopes.SINGLETON);
	}
}
