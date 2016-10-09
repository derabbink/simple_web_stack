package com.abbink.simplewebstack.ui.http.login.di;

import com.abbink.simplewebstack.ui.http.login.AboutMeResource;
import com.abbink.simplewebstack.ui.http.login.AuthenticatedResource;
import com.abbink.simplewebstack.ui.http.login.LoginResource;
import com.abbink.simplewebstack.ui.http.login.LogoutResource;
import com.google.inject.AbstractModule;

public class LoginModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(LoginResource.class);
		bind(LogoutResource.class);
		
		bind(AboutMeResource.class);
		bind(AuthenticatedResource.class);
	}
}
