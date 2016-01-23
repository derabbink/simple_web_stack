package com.abbink.simplewebstack.common.auth.di;

import javax.servlet.ServletContext;

import org.apache.shiro.guice.web.ShiroWebModule;

import com.abbink.simplewebstack.common.auth.BearerTokenAuthenticatingFilter;
import com.abbink.simplewebstack.common.auth.BearerTokenRealm;
import com.abbink.simplewebstack.common.auth.FormAuthenticationFilter;
import com.abbink.simplewebstack.common.auth.UsernamePasswordRealm;
import com.google.inject.Key;
import com.google.inject.name.Names;

class ShiroConfigurationModule extends ShiroWebModule {
	
	public static final Key<BearerTokenAuthenticatingFilter> BEARER_TOKEN = Key.get(BearerTokenAuthenticatingFilter.class);
	public static final Key<FormAuthenticationFilter> FORM = Key.get(FormAuthenticationFilter.class);
	
	private final String securityFilterPathSpec;
	
	/**
	 * Constructs a new ShiroConfigurationModule for the provided ServletContext.
	 *
	 * @param servletContext the ServletContext for this ShiroConfigurationModule
	 * @param securityFilterPathSpec the path spec that will be intercepted by the security filter chain
	 */
	public ShiroConfigurationModule(ServletContext servletContext, String securityFilterPathSpec) {
		super(servletContext);
		this.securityFilterPathSpec = securityFilterPathSpec;
	}
	
	@Override
	protected void configureShiroWeb() {
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_usernameParam")).to("username");
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_passwordParam")).to("password");
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_loginUrl")).to("/login");
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_successUrl")).to("/login");
		
		bindRealm().to(BearerTokenRealm.class);
		bindRealm().to(UsernamePasswordRealm.class);
		
		addFilterChains();
	}
	
	/**
	 * Adds the filter chains for the application.
	 */
	@SuppressWarnings("unchecked")
	protected void addFilterChains() {
//		addFilterChain(securityFilterPathSpec, AUTHC_BASIC);
//		addFilterChain("/api/**", NO_SESSION_CREATION, BEARER_TOKEN);
//		addFilterChain("/**", FORM);
	}
}
