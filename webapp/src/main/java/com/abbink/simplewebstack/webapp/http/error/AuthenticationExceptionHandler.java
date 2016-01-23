package com.abbink.simplewebstack.webapp.http.error;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.authc.AuthenticationException;

import com.abbink.simplewebstack.api.utils.Constants;
import com.abbink.simplewebstack.common.jersey.ext.SlaveExceptionMapper;

@Provider
public class AuthenticationExceptionHandler implements ExceptionMapper<AuthenticationException>{
	
	private enum Handler {
		API, UI
	}
	
	@Inject
	@Named(com.abbink.simplewebstack.api.utils.Constants.BASE_PATH_SEGMENT)
	private SlaveExceptionMapper<AuthenticationException> apiHandler;
	@Inject
	@Named(com.abbink.simplewebstack.ui.utils.Constants.BASE_PATH_SEGMENT)
	private SlaveExceptionMapper<AuthenticationException> uiHandler;
	
	@Context
	private HttpHeaders headers;
	@Context
	private HttpServletRequest request;
	@Context
	UriInfo uriInfo;
	
	public Response toResponse(AuthenticationException ex){
		try {
			return delegateToSlaveHandler(ex);
		} catch (Error e) {
			// TODO delegate to even more generic error handler
			throw e;
		}
		
	}
	
	private Response delegateToSlaveHandler(AuthenticationException ex) {
		Handler handler = Handler.UI;
		List<PathSegment> segments = uriInfo.getPathSegments();
		if (!segments.isEmpty() &&
			Constants.BASE_PATH_SEGMENT.equals(segments.iterator().next().toString())) {
			handler = Handler.API;
		}
		
		switch (handler) {
			case API:
				return apiHandler.toResponse(ex, headers, request);
			case UI:
			default:
				return uiHandler.toResponse(ex, headers, request);
		}
	}
}
