package com.abbink.simplewebstack.ui.error.jersey.di;

import javax.ws.rs.core.MediaType;

import com.abbink.simplewebstack.ui.error.jersey.NotFoundExceptionMapper;
import com.abbink.simplewebstack.ui.error.jersey.ThrowableMapper;
import com.abbink.simplewebstack.ui.error.jersey.WebAppErrorMapper;
import com.abbink.simplewebstack.common.error.WebAppError;
import com.abbink.simplewebstack.common.error.jersey.RedirectMapper;
import com.abbink.simplewebstack.common.error.jersey.SpecializedExceptionMapper;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.sun.jersey.api.NotFoundException;

public class ExceptionMappersModule extends AbstractModule {
	
	@Override
	protected void configure() {
		requireBinding(RedirectMapper.class);
		requireBinding(WebAppErrorMapper.class);
		requireBinding(NotFoundExceptionMapper.class);
		requireBinding(ThrowableMapper.class);
		
		configureWebAppErrorMapper();
		configureNotFoundExceptionMapper();
		configureThrowableMapper();
	}
	
	private void configureWebAppErrorMapper() {
		MapBinder<MediaType, SpecializedExceptionMapper<WebAppError>> mapBinder = MapBinder.newMapBinder(
			binder(),
			new TypeLiteral<MediaType>() {},
			new TypeLiteral<SpecializedExceptionMapper<WebAppError>>() {}
		);
		mapBinder.addBinding(MediaType.TEXT_HTML_TYPE).to(WebAppErrorMapper.class);
	}
	
	private void configureNotFoundExceptionMapper() {
		MapBinder<MediaType, SpecializedExceptionMapper<NotFoundException>> mapBinder = MapBinder.newMapBinder(
			binder(),
			new TypeLiteral<MediaType>() {},
			new TypeLiteral<SpecializedExceptionMapper<NotFoundException>>() {}
		);
		mapBinder.addBinding(MediaType.TEXT_HTML_TYPE).to(NotFoundExceptionMapper.class);
	}
	
	private void configureThrowableMapper() {
		MapBinder<MediaType, SpecializedExceptionMapper<Throwable>> mapBinder = MapBinder.newMapBinder(
			binder(),
			new TypeLiteral<MediaType>() {},
			new TypeLiteral<SpecializedExceptionMapper<Throwable>>() {}
		);
		mapBinder.addBinding(MediaType.TEXT_HTML_TYPE).to(ThrowableMapper.class);
	}
}	
