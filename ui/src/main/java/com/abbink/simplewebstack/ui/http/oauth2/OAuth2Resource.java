package com.abbink.simplewebstack.ui.http.oauth2;

import static com.abbink.simplewebstack.common.auth.utils.SubjectUtils.getUserIDValue;
import static com.abbink.simplewebstack.data.generated.tables.AppRoles.APP_ROLES;
import static com.abbink.simplewebstack.data.generated.tables.Apps.APPS;
import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;
import static com.abbink.simplewebstack.ui.utils.SessionKeys.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.WebAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.utils.RandomStringGenerator;
import com.abbink.simplewebstack.common.error.WebAppError;
import com.abbink.simplewebstack.data.enums.AppRolesEnum;
import com.abbink.simplewebstack.data.enums.PermissionScopesEnum;
import com.abbink.simplewebstack.data.generated.tables.pojos.AppRoles;
import com.abbink.simplewebstack.data.generated.tables.pojos.Apps;
import com.sun.jersey.api.view.Viewable;
import com.sun.jndi.toolkit.url.Uri;

@Slf4j
@Singleton
@Path(BASE_PATH + "oauth2")
@Auth(WebAuthenticationMechanism.class)
@Produces(MediaType.TEXT_HTML)
public class OAuth2Resource {
	@Inject private JdbcDataSource ds;
	@Inject private SQLDialect dialect;
	@Inject private RandomStringGenerator rsg;
	
	@GET
	public Viewable get(
		@QueryParam("app") String appXid,
		@QueryParam("scopes") String scopes,
		@QueryParam("redirect") URI redirect,
		@Context Subject subject,
		@Context UriInfo uriInfo
	) {
		log.trace("get");
		
		Collection<PermissionScopesEnum> permissions = validatePermissionScopes(scopes);
		
		Apps app;
		AppRoles role;
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			Record r = dsl.select()
				.from(APPS)
				.leftJoin(APP_ROLES).on(APPS.ID.eq(APP_ROLES.APP_ID))
				.where(APPS.XID.eq(appXid))
				.and(APP_ROLES.USER_ID.eq(getUserIDValue(subject)))
				.fetchAny();
			app = r.into(Apps.class);
			role = r.into(AppRoles.class);
		} catch (SQLException e) {
			throw new WebAppError(0, "Something went wrong.", Status.INTERNAL_SERVER_ERROR);
		}
		
		if (app == null) {
			throw new WebAppError(200, "Invalid app ID.", Status.NOT_FOUND);
		}
		
		boolean canAccess = app.getEnabled() == 1;
		if (!canAccess && role != null) {
			canAccess = role.getRole() == AppRolesEnum.admin.name();
		}
		if (!canAccess) {
			throw new WebAppError(200, "This app is disabled. Only admins can currently use it.", Status.NOT_FOUND);
		}
		
		if (!redirect.equals(URI.create(app.getRedirectUri()))) {
			throw new WebAppError(300, "Invalid redirect URI used.", Status.NOT_ACCEPTABLE);
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		Session session = subject.getSession();
		String sanitizedAppXid = app.getXid();
		session.setAttribute(LOGIN_DIALOG_APP_XID, sanitizedAppXid);
		model.put("app", app);
		String nonce = rsg.getRandomString(10);
		session.setAttribute(LOGIN_DIALOG_NONCE, nonce);
		model.put("nonce", nonce);
		session.setAttribute(LOGIN_DIALOG_SCOPES, scopes);
		model.put("permissions", permissions);
		model.put("redirectUri", redirect);
		
		return new Viewable("/oauth2/dialog", model);
	}
	
	private Collection<PermissionScopesEnum> validatePermissionScopes(String scopes) {
		if (scopes == null) {
			scopes = "";
		}
		
		String[] split = scopes.split(",");
		List<PermissionScopesEnum> result = new LinkedList<PermissionScopesEnum>();
		try {
			for (String s : split) {
				result.add(PermissionScopesEnum.valueOf(s));
			}
		} catch (IllegalArgumentException e) {
			throw new WebAppError(200, "Invalid permission scopes.", Status.NOT_FOUND, e);
		}
		
		return result;
	}
	
}
