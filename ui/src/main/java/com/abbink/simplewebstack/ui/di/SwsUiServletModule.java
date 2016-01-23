package com.abbink.simplewebstack.ui.di;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.abbink.simplewebstack.common.jersey.shiro.di.JerseyShiroModule;
import com.abbink.simplewebstack.common.metrics.response.HttpStatusCodeMetricResourceFilterFactory;
import com.abbink.simplewebstack.ui.auth.di.ShiroAuthModule;
import com.abbink.simplewebstack.ui.freemarker.di.FreemarkerModule;
import com.abbink.simplewebstack.ui.http.auth.di.AuthModule;
import com.abbink.simplewebstack.ui.http.error.di.ErrorModule;
import com.abbink.simplewebstack.ui.http.index.di.IndexModule;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class SwsUiServletModule extends ServletModule {
	
	private ServletContext servletContext;
	
	public SwsUiServletModule(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	protected void configureServlets() {
		// hook Shiro into Guice Servlet
		install(new ShiroAuthModule(servletContext));
		// Hook Shiro into Jersey
		install(new JerseyShiroModule());
		// hook Jersey into Guice Servlet
		bind(GuiceContainer.class);
		// hook Freemarker into Jersey
		install(new FreemarkerModule());
		
		install(new IndexModule());
		install(new AuthModule());
		install(new ErrorModule());
		
		Map<String, String> guiceContainerConfig = new HashMap<String, String>();
		guiceContainerConfig.put(
			ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
			HttpStatusCodeMetricResourceFilterFactory.class.getCanonicalName()
		);
		guiceContainerConfig.put(
			PackagesResourceConfig.PROPERTY_PACKAGES,
			"com.abbink.simplewebstack.ui"
		);
		serve("/*").with(GuiceContainer.class, guiceContainerConfig);
	}
	
}
