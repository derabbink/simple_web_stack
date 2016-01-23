package com.abbink.simplewebstack.common.jersey.shiro.di;

import com.abbink.simplewebstack.common.jersey.shiro.AuthInjectableProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class JerseyShiroModule extends AbstractModule {
	@Override
	protected void configure() {
		// hook Shiro into Jersey
		bind(AuthInjectableProvider.class).in(Scopes.SINGLETON);
	}
}