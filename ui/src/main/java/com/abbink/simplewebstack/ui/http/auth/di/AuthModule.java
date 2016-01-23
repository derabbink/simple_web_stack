package com.abbink.simplewebstack.ui.http.auth.di;

import com.abbink.simplewebstack.ui.http.auth.AuthAResource;
import com.abbink.simplewebstack.ui.http.auth.AuthBResource;
import com.abbink.simplewebstack.ui.http.auth.LoginResource;
import com.google.inject.AbstractModule;

public class AuthModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(LoginResource.class);
		
		bind(AuthAResource.class);
		bind(AuthBResource.class);
	}
}
