package com.abbink.simplewebstack.api.auth.di;

import javax.servlet.ServletContext;

import org.apache.shiro.guice.web.ShiroWebModule;

import com.abbink.simplewebstack.common.auth.BearerTokenAuthenticatingFilter;
import com.abbink.simplewebstack.common.auth.BearerTokenRealm;
import com.google.inject.Key;

public class ShiroAuthModule extends ShiroWebModule {
	
	public static final Key<BearerTokenAuthenticatingFilter> BEARER_TOKEN = Key.get(BearerTokenAuthenticatingFilter.class);
	
	public ShiroAuthModule(ServletContext servletContext) {
		super(servletContext);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void configureShiroWeb() {
		bindRealm().to(BearerTokenRealm.class);
		
//		addFilterChain("/api/**", BEARER_TOKEN);
	}
}
