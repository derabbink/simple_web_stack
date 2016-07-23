package com.abbink.simplewebstack.webapp;

import static com.abbink.simplewebstack.common.data.generated.tables.AccessTokens.ACCESS_TOKENS;
import static com.abbink.simplewebstack.common.data.generated.tables.AppScopedIds.APP_SCOPED_IDS;
import static com.abbink.simplewebstack.common.data.generated.tables.Apps.APPS;
import static com.abbink.simplewebstack.common.data.generated.tables.Something.SOMETHING;
import static com.abbink.simplewebstack.common.data.generated.tables.Users.USERS;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.abbink.simplewebstack.common.auth.utils.RandomStringGenerator;
import com.abbink.simplewebstack.common.data.migration.FlywayMigrator;
import com.abbink.simplewebstack.webapp.di.SwsModule;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class SwsServletContextListener extends GuiceServletContextListener {
	
	private ServletContext servletContext;
	private Injector injector;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
		
		JmxReporter reporter = JmxReporter.forRegistry(injector.getInstance(MetricRegistry.class)).build();
		reporter.start();
		
		FlywayMigrator migrator = injector.getInstance(FlywayMigrator.class);
		migrator.migrate();
		
		initializeData();
	}
	
	@Override
	protected Injector getInjector() {
		injector = Guice.createInjector(new SwsModule(servletContext));
		return injector;
	}
	
	private void initializeData() {
		JdbcDataSource ds = injector.getInstance(JdbcDataSource.class);
		SQLDialect dialect = injector.getInstance(SQLDialect.class);
		RandomStringGenerator rsg = injector.getInstance(RandomStringGenerator.class);
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			dsl.insertInto(SOMETHING, SOMETHING.NAME)
				.values("Gigantic")
				.values("Bone Machine")
				.values("Hey")
				.values("Cactus")
				.execute();
			
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String a = "aaaaaaaaaa";
			ByteSource aSalt = rng.nextBytes();
			String aSaltS = aSalt.toBase64();
			System.out.println("salt a: " + aSaltS);
			String aHash = new Sha512Hash(a, aSalt, 1024).toBase64();
			System.out.println("hash a: " + aHash);
			String b = "bbbbbbbbbb";
			ByteSource bSalt = rng.nextBytes();
			String bSaltS = bSalt.toBase64();
			System.out.println("salt b: " + bSaltS);
			String bHash = new Sha512Hash(b, bSalt, 1024).toBase64();
			System.out.println("hash b: " + bHash);
			
			dsl = DSL.using(conn, dialect);
			dsl.insertInto(USERS, USERS.XID, USERS.NAME, USERS.SALT, USERS.PASSWORD)
				.values(rsg.getRandomString(10), "Alice", aSaltS, aHash)
				.values(rsg.getRandomString(10), "Bob", bSaltS, bHash)
				.execute();
			
			dsl = DSL.using(conn, dialect);
			dsl.insertInto(APPS, APPS.XID, APPS.NAME)
				.values(rsg.getRandomString(10), "Test App")
				.execute();
			
			String appScopedAlice = rsg.getRandomString(10);
			String appScopedBob = rsg.getRandomString(10);
			dsl = DSL.using(conn, dialect);
			dsl.insertInto(APP_SCOPED_IDS, APP_SCOPED_IDS.USER_ID, APP_SCOPED_IDS.APP_SCOPED_USER_XID, APP_SCOPED_IDS.APP_ID)
				.values(1, appScopedAlice, 1)
				.values(1, appScopedBob, 2)
				.execute();
			
			dsl = DSL.using(conn, dialect);
			dsl.insertInto(ACCESS_TOKENS,
				ACCESS_TOKENS.APP_SCOPED_USER_XID, ACCESS_TOKENS.TOKEN_SCOPED_USER_XID,
				ACCESS_TOKENS.SALT, ACCESS_TOKENS.TOKEN
			)	.values(appScopedAlice, a, aSaltS, aHash)
				.values(appScopedBob, b, bSaltS, bHash)
				.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
