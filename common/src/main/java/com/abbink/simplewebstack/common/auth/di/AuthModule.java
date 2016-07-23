package com.abbink.simplewebstack.common.auth.di;

import com.abbink.simplewebstack.common.auth.jersey.di.AuthJerseyModule;
import com.abbink.simplewebstack.common.auth.mechanisms.di.AuthenticationMechanismModule;
import com.abbink.simplewebstack.common.auth.service.di.AuthServiceModule;
import com.abbink.simplewebstack.common.auth.shiro.di.AuthShiroModule;
import com.abbink.simplewebstack.common.auth.utils.di.SecurityUtilsModule;
import com.google.inject.AbstractModule;

public class AuthModule extends AbstractModule {
	
	@Override
	protected void configure() {
		install(new SecurityUtilsModule());
		
		install(new AuthShiroModule());
		install(new AuthServiceModule());
		install(new AuthenticationMechanismModule());
		install(new AuthJerseyModule());
	}
	
}
