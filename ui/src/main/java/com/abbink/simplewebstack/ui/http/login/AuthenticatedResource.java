package com.abbink.simplewebstack.ui.http.login;

import static com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.WebAuthenticationMechanism;
import com.sun.jersey.api.view.Viewable;

@Slf4j
@Singleton
@Path(BASE_PATH + "authenticated")
@Auth(WebAuthenticationMechanism.class)
@Produces(MediaType.TEXT_HTML)
public class AuthenticatedResource {
	
	@GET
	public Viewable get() {
		log.trace("get");
		return new Viewable("/auth/authenticated");
	}
	
}
