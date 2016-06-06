package com.abbink.simplewebstack.ui.http.error;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.abbink.simplewebstack.common.jersey.ext.SlaveExceptionMapper;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.view.Viewable;

public class NotFoundExceptionMapper implements SlaveExceptionMapper<NotFoundException>{
	
	public Response toResponse(NotFoundException ex, HttpHeaders headers, HttpServletRequest request){
		return Response
			.status(Response.Status.NOT_FOUND)
			.entity(new Viewable("/404"))
			.type(MediaType.TEXT_HTML)
			.build();
	}
}
