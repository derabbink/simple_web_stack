package com.abbink.simplewebstack.common.data.migration;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

public class FlywayMigrator {
	
	@Inject
	private JdbcDataSource ds;
	
	public void migrate() {
		String cp = FlywayMigrator.class.getPackage().getName();
		Flyway flyway = new Flyway();
		flyway.setDataSource(ds);
		flyway.setLocations("classpath:" + cp);
		flyway.migrate();
	}
}