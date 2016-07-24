package com.abbink.simplewebstack.common.auth.service.di;

import com.abbink.simplewebstack.common.auth.service.WebLoginService;
import com.google.inject.AbstractModule;

public class AuthServiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(WebLoginService.class);
	}
}
