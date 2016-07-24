package com.abbink.simplewebstack.api.di;

import com.abbink.simplewebstack.api.error.jersey.di.ExceptionMappersModule;
import com.abbink.simplewebstack.api.http.db.di.DbModule;
import com.abbink.simplewebstack.api.http.error.di.ErrorModule;
import com.abbink.simplewebstack.api.http.sandwich.di.SandwichModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.sun.jersey.api.core.PackagesResourceConfig;

public class SwsApiModule extends AbstractModule {
	
	@Override
	protected void configure() {
		// hook Jackson into Jersey as the POJO <-> JSON mapper
		bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
		
		// install API-specific error mappers
		install(new ExceptionMappersModule());
		
		// install resources
		install(new SandwichModule());
		install(new DbModule());
		install(new ErrorModule());
	}
	
	/**
	 * package name that contains jersey resources
	 * see {@linkplain PackagesResourceConfig.PROPERTY_PACKAGES} for more details
	 */
	public static String getResourcePackage() {
		return "com.abbink.simplewebstack.api.http";
	}
}
