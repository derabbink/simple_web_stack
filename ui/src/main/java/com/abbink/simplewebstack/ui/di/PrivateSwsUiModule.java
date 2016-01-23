package com.abbink.simplewebstack.ui.di;

import javax.servlet.ServletContext;

import org.apache.shiro.guice.web.GuiceShiroFilter;

import com.abbink.simplewebstack.ui.auth.di.ShiroAuthModule;
import com.google.inject.PrivateModule;

/**
 * Wrap this part of the app in a {@link PrivateModule} to avoid duplicate binding conflicts.
 */
public class PrivateSwsUiModule extends PrivateModule {
	private ServletContext servletContext;
	
	public PrivateSwsUiModule(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	protected void configure() {
		// hook Shiro into Guice Servlet
		install(new ShiroAuthModule(servletContext));
		
//		expose(GuiceShiroFilter.class);
//		install(ShiroAuthModule.guiceFilterModule("*"));
	}
	
}
