package com.abbink.simplewebstack.common.auth.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;

@Slf4j
public class WebLoginService {
	
	private WebSecurityManager securityManager;
	
	@Inject
	public WebLoginService(WebSecurityManager securityManager) {
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
			log.trace("Login successful for username {}", username);
			return true;
		} catch (AuthenticationException ae) {
			log.trace("Login failed for username {}", username);
			return false;
		}
	}
}
