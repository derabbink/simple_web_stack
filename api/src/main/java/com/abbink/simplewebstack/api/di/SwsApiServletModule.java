package com.abbink.simplewebstack.api.di;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.abbink.simplewebstack.api.auth.di.ShiroAuthModule;
import com.abbink.simplewebstack.api.http.db.di.DbModule;
import com.abbink.simplewebstack.api.http.error.di.ErrorModule;
import com.abbink.simplewebstack.api.http.sandwich.di.SandwichModule;
import com.abbink.simplewebstack.common.auth.BearerTokenRealm;
import com.abbink.simplewebstack.common.jersey.shiro.AuthInjectableProvider;
import com.abbink.simplewebstack.common.jersey.shiro.di.JerseyShiroModule;
import com.abbink.simplewebstack.common.metrics.response.HttpStatusCodeMetricResourceFilterFactory;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class SwsApiServletModule extends ServletModule {
	
	private ServletContext servletContext;
	
	public SwsApiServletModule(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	protected void configureServlets() {
		// hook Shiro into Guice Servlet
		//install(new ShiroAuthModule(servletContext));
		requireBinding(BearerTokenRealm.class);
		// Hook Shiro into Jersey
		//install(new JerseyShiroModule());
		requireBinding(AuthInjectableProvider.class);
		// hook Jersey into Guice Servlet
		bind(GuiceContainer.class);
		// hook Jackson into Jersey as the POJO <-> JSON mapper
		bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
		
		install(new SandwichModule());
		install(new DbModule());
		install(new ErrorModule());
		
		Map<String, String> guiceContainerConfig = new HashMap<String, String>();
		guiceContainerConfig.put(
			ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
			HttpStatusCodeMetricResourceFilterFactory.class.getCanonicalName()
		);
		guiceContainerConfig.put(
			PackagesResourceConfig.PROPERTY_PACKAGES,
			"com.abbink.simplewebstack.api"
		);
		serve("/*").with(GuiceContainer.class, guiceContainerConfig);
	}
}
