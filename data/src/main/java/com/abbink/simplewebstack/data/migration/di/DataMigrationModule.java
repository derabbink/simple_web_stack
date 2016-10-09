package com.abbink.simplewebstack.data.migration.di;

import com.abbink.simplewebstack.data.migration.FlywayMigrator;
import com.google.inject.AbstractModule;

public class DataMigrationModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(FlywayMigrator.class);
	}
	
}
