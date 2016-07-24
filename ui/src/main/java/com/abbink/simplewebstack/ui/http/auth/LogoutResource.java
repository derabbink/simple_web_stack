package com.abbink.simplewebstack.ui.http.auth;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.subject.Subject;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.WebAuthenticationMechanism;
import com.abbink.simplewebstack.common.http.Redirect;
import com.sun.jersey.api.view.Viewable;

@Path(BASE_PATH + "logout")
@Auth(WebAuthenticationMechanism.class)
@Produces(MediaType.TEXT_HTML)
public class LogoutResource {
	
	@GET
	public Viewable get(
		@Context Subject subject,
		@Context UriInfo uriInfo
	) {
		subject.logout();
		throw new Redirect(uriInfo.getBaseUriBuilder().path(LoginResource.class).build());
	}
}
