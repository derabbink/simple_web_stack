package com.abbink.simplewebstack.common.auth.service.di;

import org.apache.shiro.web.mgt.WebSecurityManager;

import com.abbink.simplewebstack.common.auth.service.WebLoginService;
import com.google.inject.AbstractModule;

public class AuthServiceModule extends AbstractModule {
	@Override
	protected void configure() {
		requireBinding(WebSecurityManager.class);
		
		bind(WebLoginService.class);
	}
}
