package com.abbink.simplewebstack.api.http.error;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;

import com.abbink.simplewebstack.common.jersey.ext.SlaveExceptionMapper;

public class AuthenticationExceptionHandler implements SlaveExceptionMapper<AuthenticationException>{
	
	public Response toResponse(AuthenticationException ex, HttpHeaders headers, HttpServletRequest request) {
		if (ex instanceof CredentialsException) {
			// supplied principals & credentials are incorrect
			// sub types: ExpiredCredentialsException, IncorrectCredentialsException
			if (ex instanceof ExpiredCredentialsException) {
				return getExpiredTokenError();
			}
			if (ex instanceof IncorrectCredentialsException) {
				return getInvalidTokenError();
			}
		}
		if (ex instanceof AccountException) {
			// an issue with the account prevents authentication
			// sub types: ConcurrentAccessException, DisabledAccountException (LockedAccountException), ExcessiveAttemptsException, UnknownAccountException
			if (ex instanceof UnknownAccountException) {
				return getInvalidTokenError();
			}
		}
		if (ex instanceof UnsupportedTokenException) {
			// can't verify supplied principals & credentials
		}
		
		// intentionally vague error:
		return Response
			.status(Response.Status.FORBIDDEN)
			.entity(new ApiError(100, "Unsupported request."))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
	
	private Response getInvalidTokenError() {
		return Response
			.status(Response.Status.FORBIDDEN)
			.entity(new ApiError(201, "Invalid access token."))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
	
	private Response getExpiredTokenError() {
		return Response
			.status(Response.Status.FORBIDDEN)
			.entity(new ApiError(202, "Access token expired."))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
}
