package com.abbink.simplewebstack.common.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

public class BearerTokenAuthenticatingFilter extends AuthenticatingFilter {
	
	public static final String TOKEN_PARAM_NAME = "access_token";
	public static final String REQUEST_ALLOWED_ATTRIBUTE_KEY = BearerTokenAuthenticatingFilter.class.getPackage().getName() + ".request_allowed";
	public static final String ERROR_ATTRIBUTE_KEY = BearerTokenAuthenticatingFilter.class.getPackage().getName() + ".shiro_authentication_exception_name";
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		String token = getToken(request);
		return new BearerTokenAuthenticationToken(token);
	}
	
	private String getToken(ServletRequest request) {
		return request.getParameter(TOKEN_PARAM_NAME);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return executeLogin(request, response);
	}
	
	@Override
	protected boolean onLoginFailure(
		AuthenticationToken token,
		AuthenticationException e,
		ServletRequest request,
		ServletResponse response
	) {
		setRequestAttributes(request, e);
		changeRequestToErrorHandler(request, response);
		// don't continue request (false). authentication failure should be handled by redirected-to controller
		return false;
	}
	
	protected void setRequestAttributes(ServletRequest request, AuthenticationException ae) {
		request.setAttribute(REQUEST_ALLOWED_ATTRIBUTE_KEY, true);
		String className = ae.getClass().getName();
		request.setAttribute(ERROR_ATTRIBUTE_KEY, className);
	}
	
	protected void changeRequestToErrorHandler(ServletRequest request, ServletResponse response) {
		RequestDispatcher disp = request.getRequestDispatcher("/api/403");
		try {
			disp.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
