package com.abbink.simplewebstack.common.data.di;

import javax.inject.Named;
import javax.inject.Singleton;

import org.h2.jdbcx.JdbcDataSource;
import org.jooq.SQLDialect;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class DataModule extends AbstractModule {
	public static final String JDBC_URL = "JDBC_URL";
	public static final String SQL_DIALECT = "SQL_DIALECT";
	
	@Override
	protected void configure() {
		requireBinding(Key.get(String.class, Names.named(JDBC_URL)));
		requireBinding(Key.get(String.class, Names.named(SQL_DIALECT)));
	}
	
	@Provides @Singleton
	private JdbcDataSource provideJdbcDataSource(@Named(JDBC_URL) String jdbcUrl) {
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL(jdbcUrl);
		return ds;
	}
	
	@Provides @Singleton
	private SQLDialect provideSqlDialect(@Named(SQL_DIALECT) String sqlDialect) {
		return SQLDialect.valueOf(sqlDialect);
	}
}
