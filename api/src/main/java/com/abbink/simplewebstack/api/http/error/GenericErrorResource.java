package com.abbink.simplewebstack.api.http.error;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path(BASE_PATH + "error")
public class GenericErrorResource {
	
	@GET
	public ApiError getError(@Context HttpServletRequest httpRequest) {
		return throwException(httpRequest);
	}
	
	private ApiError throwException(HttpServletRequest httpRequest) {
		throw new Error("A generic error was thrown.");
	}
	
}
