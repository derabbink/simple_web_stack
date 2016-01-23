package com.abbink.simplewebstack.ui.http.error;

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
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.view.Viewable;

public class AuthenticationExceptionHandler implements SlaveExceptionMapper<AuthenticationException>{
	
	public Response toResponse(AuthenticationException ex, HttpHeaders headers, HttpServletRequest request) {
		// this exception should never be thrown in a UI context, as Shiro's filters should handle them gracefully.
		// but just in case, we implement a generic 403 error page
		
		// intentionally vague error:
		return Response
			.status(Response.Status.FORBIDDEN)
			.entity(new Viewable("/403"))
			.type(MediaType.TEXT_HTML)
			.build();
	}
}
