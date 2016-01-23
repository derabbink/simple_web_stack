package com.abbink.simplewebstack.common.auth;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.guice.ShiroModule;

/**
 * Only exists to call {@linkplain #setUsernameParam(String)}, {@linkplain #setPasswordParam(String)}
 * and {@linkplain #setLoginUrl(String)} from the constructor due to the lack of docs for the
 * {@link ShiroModule#bindBeanType bindBeanType}
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	@Inject
	public FormAuthenticationFilter(
		@Named("FormAuthenticationFilter_usernameParam") String usernameParam,
		@Named("FormAuthenticationFilter_passwordParam") String passwordParam,
		@Named("FormAuthenticationFilter_loginUrl") String loginUrl,
		@Named("FormAuthenticationFilter_successUrl") String successUrl
	) {
		super();
		setUsernameParam(usernameParam);
		setPasswordParam(passwordParam);
		setLoginUrl(loginUrl);
		setSuccessUrl(successUrl);
	}
}
