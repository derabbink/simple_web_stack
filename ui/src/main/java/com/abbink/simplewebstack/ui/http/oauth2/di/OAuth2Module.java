package com.abbink.simplewebstack.ui.http.oauth2.di;

import com.abbink.simplewebstack.ui.http.oauth2.OAuth2Resource;
import com.google.inject.AbstractModule;

public class OAuth2Module extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(OAuth2Resource.class);
	}
	
}
