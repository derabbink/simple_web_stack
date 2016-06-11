package com.abbink.simplewebstack.codegen.di;

import com.abbink.simplewebstack.codegen.data.jooq.di.JooqModule;
import com.abbink.simplewebstack.common.data.di.DataModule;
import com.abbink.simplewebstack.common.data.migration.di.DataMigrationModule;
import com.google.inject.AbstractModule;

public class CodegenModule extends AbstractModule {
	
	@Override
	protected void configure() {
		install(new DataModule());
		install(new DataMigrationModule());
		install(new JooqModule());
	}
	
}
