package com.abbink.simplewebstack.codegen;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.Configuration;

import com.abbink.simplewebstack.codegen.di.CodegenModule;
import com.abbink.simplewebstack.common.data.migration.FlywayMigrator;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	
	public static void main(String... args) {
		Injector injector = Guice.createInjector(new CodegenModule());
		
		//create tables
		FlywayMigrator migrator = injector.getInstance(FlywayMigrator.class);
		migrator.migrate();
		
		//jOOQ code generation
		JdbcDataSource ds = injector.getInstance(JdbcDataSource.class);
		try (Connection c = ds.getConnection()) {
			Configuration conf = injector.getInstance(Configuration.class);
			GenerationTool tool = new GenerationTool();
			tool.setConnection(c);
			tool.run(conf);
		} catch (SQLException e) {
			// sql connection problems
			e.printStackTrace();
		} catch (Exception e) {
			// run failed
			e.printStackTrace();
		}
	}
}
