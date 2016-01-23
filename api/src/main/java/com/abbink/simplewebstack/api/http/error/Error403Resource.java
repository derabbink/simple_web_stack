package com.abbink.simplewebstack.api.http.error;

import static com.abbink.simplewebstack.api.utils.Constants.BASE_PATH;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;

import com.abbink.simplewebstack.common.auth.BearerTokenAuthenticatingFilter;
import com.sun.jersey.api.NotFoundException;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path(BASE_PATH + "403")
public class Error403Resource {
	
	@GET
	public ApiError getError(@Context HttpServletRequest httpRequest) {
		return throwException(httpRequest);
	}
	
	private ApiError throwException(HttpServletRequest httpRequest) {
		Object attrib = httpRequest.getAttribute(BearerTokenAuthenticatingFilter.REQUEST_ALLOWED_ATTRIBUTE_KEY);
		boolean requestAllowed = false;
		if (attrib != null && attrib instanceof Boolean) {
			requestAllowed = (Boolean) attrib;
		}
		if (!requestAllowed) {
			throw new NotFoundException();
		}
		
		attrib = httpRequest.getAttribute(BearerTokenAuthenticatingFilter.ERROR_ATTRIBUTE_KEY);
		if (attrib != null && attrib instanceof String) {
			String ex = (String) attrib;
			
			if (ex == CredentialsException.class.getName()) {
				// supplied principals & credentials are incorrect
				// sub types: ExpiredCredentialsException, IncorrectCredentialsException
				throw new CredentialsException();
			}
			if (ex == ExpiredCredentialsException.class.getName()) {
				throw new ExpiredCredentialsException();
			}
			if (ex == IncorrectCredentialsException.class.getName()) {
				throw new IncorrectCredentialsException();
			}
			
			if (ex == AccountException.class.getName()) {
				// an issue with the account prevents authentication
				// sub types: ConcurrentAccessException, DisabledAccountException (LockedAccountException), ExcessiveAttemptsException, UnknownAccountException
				throw new AccountException();
			}
			if (ex == UnknownAccountException.class.getName()) {
				throw new UnknownAccountException();
			}
			
			if (ex == UnsupportedTokenException.class.getName()) {
				// can't verify supplied principals & credentials
				throw new UnsupportedTokenException();
			}
		}
		
		// intentionally vague error:
		throw new AuthenticationException();
	}
}
