package com.abbink.simplewebstack.ui.http.error.di;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH_SEGMENT;

import org.apache.shiro.authc.AuthenticationException;

import com.abbink.simplewebstack.common.jersey.ext.SlaveExceptionMapper;
import com.abbink.simplewebstack.ui.http.error.AuthenticationExceptionHandler;
import com.abbink.simplewebstack.ui.http.error.NotFoundExceptionHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.sun.jersey.api.NotFoundException;

public class ErrorModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(new TypeLiteral<SlaveExceptionMapper<NotFoundException>>(){})
			.annotatedWith(Names.named(BASE_PATH_SEGMENT))
			.to(NotFoundExceptionHandler.class)
			.in(Scopes.SINGLETON);
		bind(new TypeLiteral<SlaveExceptionMapper<AuthenticationException>>(){})
			.annotatedWith(Names.named(BASE_PATH_SEGMENT))
			.to(AuthenticationExceptionHandler.class)
			.in(Scopes.SINGLETON);
	}
}
