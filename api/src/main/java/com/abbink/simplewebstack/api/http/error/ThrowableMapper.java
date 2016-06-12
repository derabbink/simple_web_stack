package com.abbink.simplewebstack.api.http.error;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.abbink.simplewebstack.common.errors.jersey.SlaveExceptionMapper;

public class ThrowableMapper implements SlaveExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception, HttpHeaders headers, HttpServletRequest request) {
		return Response
			.status(Status.INTERNAL_SERVER_ERROR)
			.entity(new ApiError(0, "An unknown error occurred."))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
	
}
