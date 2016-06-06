package com.abbink.simplewebstack.common.auth.shiro.di;

import com.abbink.simplewebstack.common.auth.shiro.BearerTokenRealm;
import com.abbink.simplewebstack.common.auth.shiro.BearerTokenSecurityManager;
import com.abbink.simplewebstack.common.auth.shiro.UsernamePasswordRealm;
import com.google.inject.AbstractModule;

public class AuthShiroModule extends AbstractModule {
	
	@Override
	protected void configure() {
		// bind realms
		bind(BearerTokenRealm.class);
		bind(UsernamePasswordRealm.class);
		
		// bind SecurityManagers
		bind(BearerTokenSecurityManager.class);
	}
	
}
