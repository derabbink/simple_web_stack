package com.abbink.simplewebstack.webapp.http.error;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.abbink.simplewebstack.api.utils.Constants;
import com.abbink.simplewebstack.common.errors.WebAppError;
import com.abbink.simplewebstack.common.jersey.ext.SlaveExceptionMapper;

@Singleton
@Provider
public class WebAppErrorHandler implements ExceptionMapper<WebAppError> {
	
	@Inject
	@Named(com.abbink.simplewebstack.api.utils.Constants.BASE_PATH_SEGMENT)
	private SlaveExceptionMapper<WebAppError> apiMapper;
	@Inject
	@Named(com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH_SEGMENT)
	private SlaveExceptionMapper<WebAppError> uiMapper;
	
	@Context
	private HttpHeaders headers;
	@Context
	private HttpServletRequest request;
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response toResponse(WebAppError exception) {
		HandlerType handler = HandlerType.UI;
		List<PathSegment> segments = uriInfo.getPathSegments();
		if (!segments.isEmpty() &&
			Constants.BASE_PATH_SEGMENT.equals(segments.iterator().next().toString())) {
			handler = HandlerType.API;
		}
		
		switch (handler) {
			case API:
				return apiMapper.toResponse(exception, headers, request);
			case UI:
			default:
				return uiMapper.toResponse(exception, headers, request);
		}
	}
}
