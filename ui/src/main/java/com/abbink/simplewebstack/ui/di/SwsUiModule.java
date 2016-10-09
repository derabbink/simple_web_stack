package com.abbink.simplewebstack.ui.di;

import com.abbink.simplewebstack.ui.error.jersey.di.ExceptionMappersModule;
import com.abbink.simplewebstack.ui.freemarker.di.FreemarkerModule;
import com.abbink.simplewebstack.ui.http.index.di.IndexModule;
import com.abbink.simplewebstack.ui.http.login.di.LoginModule;
import com.abbink.simplewebstack.ui.http.oauth2.di.OAuth2Module;
import com.google.inject.AbstractModule;
import com.sun.jersey.api.core.PackagesResourceConfig;

public class SwsUiModule extends AbstractModule {
	
	@Override
	protected void configure() {
		// hook Freemarker into Jersey
		install(new FreemarkerModule());
		
		// install UI-specific error mappers
		install(new ExceptionMappersModule());
		
		// install resources
		install(new IndexModule());
		install(new LoginModule());
		install(new OAuth2Module());
	}
	
	/**
	 * package name that contains jersey resources
	 * see {@linkplain PackagesResourceConfig.PROPERTY_PACKAGES} for more details
	 * Only required if you don't want to bind all resources using Guice
	 */
	public static String getResourcePackage() {
		return "com.abbink.simplewebstack.ui.http";
	}
	
}
