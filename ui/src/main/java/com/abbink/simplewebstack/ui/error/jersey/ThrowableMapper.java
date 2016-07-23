package com.abbink.simplewebstack.ui.error.jersey;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.abbink.simplewebstack.common.error.jersey.SpecializedExceptionMapper;
import com.sun.jersey.api.view.Viewable;

public class ThrowableMapper implements SpecializedExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception, HttpHeaders headers, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("code", 0);
		model.put("message", "An unknown error occurred.");
		return Response
			.status(Status.INTERNAL_SERVER_ERROR)
			.entity(new Viewable("/error", model))
			.type(MediaType.TEXT_HTML)
			.build();
	}
}
