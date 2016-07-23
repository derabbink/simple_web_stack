package com.abbink.simplewebstack.api.error.jersey;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.abbink.simplewebstack.api.error.JsonError;
import com.abbink.simplewebstack.common.error.jersey.SpecializedExceptionMapper;
import com.sun.jersey.api.NotFoundException;

public class NotFoundExceptionMapper implements SpecializedExceptionMapper<NotFoundException>{
	
	public Response toResponse(NotFoundException ex, HttpHeaders headers, HttpServletRequest request){
		return Response
			.status(Response.Status.NOT_FOUND)
			.entity(new JsonError(100, "Endpoint not found."))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
}
