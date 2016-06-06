package com.abbink.simplewebstack.api.http.error.di;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH_SEGMENT;

import com.abbink.simplewebstack.api.http.error.Error403Resource;
import com.abbink.simplewebstack.api.http.error.NotFoundExceptionMapper;
import com.abbink.simplewebstack.api.http.error.ThrowableMapper;
import com.abbink.simplewebstack.api.http.error.WebAppErrorMapper;
import com.abbink.simplewebstack.common.errors.WebAppError;
import com.abbink.simplewebstack.common.jersey.ext.SlaveExceptionMapper;
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
			.to(NotFoundExceptionMapper.class)
			.in(Scopes.SINGLETON);
		bind(new TypeLiteral<SlaveExceptionMapper<WebAppError>>(){})
			.annotatedWith(Names.named(BASE_PATH_SEGMENT))
			.to(WebAppErrorMapper.class)
			.in(Scopes.SINGLETON);
		bind(new TypeLiteral<SlaveExceptionMapper<Throwable>>(){})
			.annotatedWith(Names.named(BASE_PATH_SEGMENT))
			.to(ThrowableMapper.class)
			.in(Scopes.SINGLETON);
		
		bind(Error403Resource.class);
	}
}
