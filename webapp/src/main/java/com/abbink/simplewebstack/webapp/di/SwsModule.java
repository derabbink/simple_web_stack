package com.abbink.simplewebstack.webapp.di;

import java.util.HashMap;
import java.util.Map;

import com.abbink.simplewebstack.api.di.SwsApiModule;
import com.abbink.simplewebstack.common.auth.di.AuthModule;
import com.abbink.simplewebstack.common.auth.jersey.AuthResourceFilterFactory;
import com.abbink.simplewebstack.common.data.di.DataModule;
import com.abbink.simplewebstack.common.data.migration.di.DataMigrationModule;
import com.abbink.simplewebstack.common.error.jersey.di.ExceptionMappersModule;
import com.abbink.simplewebstack.common.metrics.di.MetricsModule;
import com.abbink.simplewebstack.common.metrics.response.HttpStatusCodeMetricResourceFilterFactory;
import com.abbink.simplewebstack.ui.di.SwsUiModule;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class SwsModule extends ServletModule {
	
	@Override
	protected void configureServlets() {
		install(new ConfigModule());
		
		install(new MetricsModule());
		install(new DataModule());
		install(new DataMigrationModule());
		
		install(new AuthModule());
		
		// hook Jersey into Guice Servlet
		bind(GuiceContainer.class);
		install(new ExceptionMappersModule());
		install(new SwsApiModule());
		install(new SwsUiModule());
		
		
		Map<String, String> guiceContainerConfig = new HashMap<String, String>();
		guiceContainerConfig.put(
			ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
			HttpStatusCodeMetricResourceFilterFactory.class.getCanonicalName() +","+
			AuthResourceFilterFactory.class.getCanonicalName()
		);
		// Specifying this is merely good form. Most likely it will work without this as well
		guiceContainerConfig.put(
			PackagesResourceConfig.PROPERTY_PACKAGES,
			SwsApiModule.getResourcePackage() + ";" +SwsUiModule.getResourcePackage()
		);
		serve("/*").with(GuiceContainer.class, guiceContainerConfig);
	}
	
}
