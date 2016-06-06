package com.abbink.simplewebstack.api.http.error;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.abbink.simplewebstack.common.errors.WebAppError;
import com.abbink.simplewebstack.common.jersey.ext.SlaveExceptionMapper;

public class WebAppErrorMapper implements SlaveExceptionMapper<WebAppError> {

	@Override
	public Response toResponse(WebAppError exception, HttpHeaders headers, HttpServletRequest request) {
		return Response
			.status(exception.getResponse().getStatus())
			.entity(new ApiError(exception.getCode(), exception.getMessage()))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
	
}
