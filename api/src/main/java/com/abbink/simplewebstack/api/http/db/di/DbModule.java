package com.abbink.simplewebstack.api.http.db.di;

import com.abbink.simplewebstack.api.http.db.DbResource;
import com.google.inject.AbstractModule;

public class DbModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(DbResource.class);
	}
	
}
