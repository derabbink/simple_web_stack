package com.abbink.simplewebstack.webapp.http.error.di;

import com.abbink.simplewebstack.webapp.http.error.NotFoundExceptionHandler;
import com.abbink.simplewebstack.webapp.http.error.ThrowableHandler;
import com.abbink.simplewebstack.webapp.http.error.WebAppErrorHandler;
import com.google.inject.AbstractModule;

public class ErrorModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(NotFoundExceptionHandler.class);
		bind(WebAppErrorHandler.class);
		bind(ThrowableHandler.class);
	}
}
