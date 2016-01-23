package com.abbink.simplewebstack.common.data.di;

import org.h2.jdbcx.JdbcDataSource;
import org.jooq.SQLDialect;

import com.google.inject.AbstractModule;

public class DataModule extends AbstractModule {
	
	private String DbUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private SQLDialect sqlDialect = SQLDialect.H2;
	
	@Override
	protected void configure() {
		bind(JdbcDataSource.class).toInstance(getDataSource());
		bind(SQLDialect.class).toInstance(sqlDialect);
	}
	
	private JdbcDataSource getDataSource() {
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL(DbUrl);
		return ds;
	}
}
