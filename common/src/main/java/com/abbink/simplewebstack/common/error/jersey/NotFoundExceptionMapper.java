package com.abbink.simplewebstack.common.error.jersey;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.java.Log;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.core.ExtendedUriInfo;
import com.sun.jersey.api.model.AbstractResourceMethod;

@Log
@Singleton
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
	
	private Map<MediaType, SpecializedExceptionMapper<NotFoundException>> specializedExceptionMappers;
	@Context private ExtendedUriInfo xUriInfo;
	@Context private HttpHeaders headers;
	@Context private HttpServletRequest request;
	
	@Inject
	public NotFoundExceptionMapper(
		Map<MediaType, SpecializedExceptionMapper<NotFoundException>> specializedExceptionMappers
	) {
		this.specializedExceptionMappers = specializedExceptionMappers;
	}
	
	@Override
	public Response toResponse(NotFoundException exception) {
		log.log(Level.INFO, exception.getMessage(), exception);
		AbstractResourceMethod am = xUriInfo.getMatchedMethod();
		List<MediaType> mediaTypes = null;
		if (am != null) {
			mediaTypes = am.getSupportedOutputTypes();
		}
		List<MediaType> mediaTypes2 = headers.getAcceptableMediaTypes();
		if (mediaTypes == null) {
			mediaTypes = mediaTypes2;
		} else {
			mediaTypes.addAll(mediaTypes2);
		}
		if (mediaTypes != null) {
			SpecializedExceptionMapper<NotFoundException> mapper = null;
			Iterator<MediaType> iter = mediaTypes.iterator();
			while (mapper == null && iter.hasNext()) {
				mapper = specializedExceptionMappers.get(iter.next());
			}
			if (mapper != null) {
				return mapper.toResponse(exception, headers, request);
			}
		}
		
		// default behavior
		return Response
			.status(Status.INTERNAL_SERVER_ERROR)
			.entity("Error 404\nResource not found.\n")
			.type(MediaType.TEXT_PLAIN)
			.build();
	}
}
