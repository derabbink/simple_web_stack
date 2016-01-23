package com.abbink.simplewebstack.api.auth.di;

import javax.servlet.ServletContext;

import com.google.inject.PrivateModule;

/**
 * Wrap this part of the app in a {@link PrivateModule} to avoid duplicate binding conflicts.
 */
public class PrivateShiroAuthModule extends PrivateModule {
	private ServletContext servletContext;
	
	public PrivateShiroAuthModule(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	protected void configure() {
		//bindGuiceFilter("/api/*", binder());
		install(new ShiroAuthModule(servletContext));
	}
}
