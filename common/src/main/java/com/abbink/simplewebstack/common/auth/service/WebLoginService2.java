package com.abbink.simplewebstack.common.auth.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;

@Log
public class WebLoginService2 {
	
	private WebSecurityManager securityManager;
	
	@Inject
	public WebLoginService2(WebSecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	
	public boolean executeLogin(
		String username,
		String password,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		WebSubject subject = new WebSubject.Builder(securityManager, request, response).buildWebSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
		try {
			subject.login(token);
			return true;
		} catch (AuthenticationException ae) {
			log.info("Unsuccessful login");
			return false;
		}
	}
}
