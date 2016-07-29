package com.abbink.simplewebstack.ui.http.auth.di;

import com.abbink.simplewebstack.ui.http.auth.AboutMeResource;
import com.abbink.simplewebstack.ui.http.auth.AuthenticatedResource;
import com.abbink.simplewebstack.ui.http.auth.LoginResource;
import com.abbink.simplewebstack.ui.http.auth.LogoutResource;
import com.google.inject.AbstractModule;

public class AuthModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(LoginResource.class);
		bind(LogoutResource.class);
		
		bind(AboutMeResource.class);
		bind(AuthenticatedResource.class);
	}
}
