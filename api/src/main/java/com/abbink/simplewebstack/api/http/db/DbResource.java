package com.abbink.simplewebstack.api.http.db;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH;
import static com.abbink.simplewebstack.data.generated.tables.Something.SOMETHING;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.abbink.simplewebstack.data.generated.tables.pojos.Something;
import com.sun.jersey.api.NotFoundException;

@Slf4j
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path(BASE_PATH + "db")
public class DbResource {
	
	@Inject
	private JdbcDataSource ds;
	
	@GET
	public Something get() {
		log.trace("get");
		try (Connection conn = ds.getConnection()) {
			DSLContext create = DSL.using(conn, SQLDialect.H2);
			Something result = create.selectFrom(SOMETHING)
				.where(SOMETHING.ID.eq(1))
				.fetchAny()
				.into(Something.class);
			return result;
		} catch (SQLException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
}
