package com.abbink.simplewebstack.webapp.di;

import javax.servlet.ServletContext;

import com.abbink.simplewebstack.api.di.SwsApiServletModule;
import com.abbink.simplewebstack.common.auth.di.SecurityConfigModule;
import com.abbink.simplewebstack.common.data.di.DataModule;
import com.abbink.simplewebstack.common.data.migration.di.DataMigrationModule;
import com.abbink.simplewebstack.common.metrics.di.MetricsModule;
import com.abbink.simplewebstack.ui.di.SwsUiServletModule;
import com.abbink.simplewebstack.webapp.http.error.di.ErrorModule;
import com.google.inject.AbstractModule;

public class SwsModule extends AbstractModule {
	
	private ServletContext servletContext;
	
	public SwsModule(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	protected void configure() {
		install(new MetricsModule());
		install(new DataModule());
		install(new DataMigrationModule());
		
		install(new SecurityConfigModule());
		
		install(new SwsApiServletModule(servletContext));
		install(new SwsUiServletModule(servletContext));
		install(new ErrorModule());
	}
	
}
