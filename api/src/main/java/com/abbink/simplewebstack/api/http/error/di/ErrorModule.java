package com.abbink.simplewebstack.api.http.error.di;

import com.abbink.simplewebstack.api.http.error.Error403Resource;
import com.google.inject.AbstractModule;

public class ErrorModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Error403Resource.class);
	}
}
