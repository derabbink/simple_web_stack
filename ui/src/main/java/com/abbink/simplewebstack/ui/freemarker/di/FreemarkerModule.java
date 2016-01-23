package com.abbink.simplewebstack.ui.freemarker.di;

import com.abbink.simplewebstack.ui.freemarker.FreemarkerConfiguration;
import com.abbink.simplewebstack.ui.freemarker.FreemarkerViewProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import freemarker.template.Configuration;

public class FreemarkerModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Configuration.class).to(FreemarkerConfiguration.class);
		
		// hook Freemarker into Jersey
		bind(FreemarkerViewProcessor.class).in(Scopes.SINGLETON);
	}
}
