package com.abbink.simplewebstack.codegen.di;

import static com.abbink.simplewebstack.data.di.DataModule.JDBC_URL;
import static com.abbink.simplewebstack.data.di.DataModule.SQL_DIALECT;
import static com.abbink.simplewebstack.codegen.data.jooq.di.JooqModule.*;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class ConfigModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(Key.get(String.class, Names.named(JDBC_URL))).toInstance("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		bind(Key.get(String.class, Names.named(SQL_DIALECT))).toInstance("H2");
		
		bind(Key.get(String.class, Names.named(JOOQ_PACKAGE_NAME))).toInstance("com.abbink.simplewebstack.data.generated");
		bind(Key.get(String.class, Names.named(JOOQ_DIRECTORY))).toInstance("../data/src/main/java");
	}
	
}
