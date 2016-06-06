package com.abbink.simplewebstack.ui.di;

import com.abbink.simplewebstack.ui.freemarker.di.FreemarkerModule;
import com.abbink.simplewebstack.ui.http.auth.di.AuthModule;
import com.abbink.simplewebstack.ui.http.error.di.ErrorModule;
import com.abbink.simplewebstack.ui.http.index.di.IndexModule;
import com.google.inject.AbstractModule;
import com.sun.jersey.api.core.PackagesResourceConfig;

public class SwsUiModule extends AbstractModule {
	
	@Override
	protected void configure() {
		// hook Freemarker into Jersey
		install(new FreemarkerModule());
		
		// install resources
		install(new IndexModule());
		install(new AuthModule());
		install(new ErrorModule());
	}
	
	/**
	 * package name that contains jersey resources
	 * see {@linkplain PackagesResourceConfig.PROPERTY_PACKAGES} for more details
	 */
	public static String getResourcePackage() {
		return "com.abbink.simplewebstack.ui";
	}
	
}
