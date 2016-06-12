package com.abbink.simplewebstack.ui.http.error;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.abbink.simplewebstack.common.errors.WebAppError;
import com.abbink.simplewebstack.common.errors.jersey.SlaveExceptionMapper;
import com.sun.jersey.api.view.Viewable;

public class WebAppErrorMapper implements SlaveExceptionMapper<WebAppError> {

	@Override
	public Response toResponse(WebAppError exception, HttpHeaders headers, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("code", exception.getCode());
		model.put("message", exception.getMessage());
		return Response
			.status(exception.getResponse().getStatus())
			.entity(new Viewable("/error", model))
			.type(MediaType.TEXT_HTML)
			.build();
	}
}
