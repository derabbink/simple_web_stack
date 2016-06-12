package com.abbink.simplewebstack.api.http.error;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.abbink.simplewebstack.common.errors.jersey.SlaveExceptionMapper;
import com.sun.jersey.api.NotFoundException;

public class NotFoundExceptionMapper implements SlaveExceptionMapper<NotFoundException>{
	
	public Response toResponse(NotFoundException ex, HttpHeaders headers, HttpServletRequest request){
		return Response
			.status(Response.Status.NOT_FOUND)
			.entity(new ApiError(100, "Endpoint not found."))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
}
