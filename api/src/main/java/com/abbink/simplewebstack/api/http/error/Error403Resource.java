package com.abbink.simplewebstack.api.http.error;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.BearerTokenAuthenticationMechanism;
import com.abbink.simplewebstack.common.errors.ForbiddenException;

@Singleton
@Auth(BearerTokenAuthenticationMechanism.class)
@Produces(MediaType.APPLICATION_JSON)
@Path(BASE_PATH + "403")
public class Error403Resource {
	
	@GET
	public ApiError getError(@Context HttpServletRequest httpRequest) {
		return throwException(httpRequest);
	}
	
	private ApiError throwException(HttpServletRequest httpRequest) {
		throw new ForbiddenException(100, "Unsupported request.");
	}
}
