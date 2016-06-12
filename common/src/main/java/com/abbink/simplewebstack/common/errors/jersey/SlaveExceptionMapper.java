package com.abbink.simplewebstack.common.errors.jersey;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public interface SlaveExceptionMapper<E extends Throwable> {
	
	public Response toResponse(E exception, HttpHeaders headers, HttpServletRequest request);
	
}
