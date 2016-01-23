package com.abbink.simplewebstack.common.metrics.di;


import com.abbink.simplewebstack.common.metrics.response.HttpStatusCodeMetricResourceFilterFactory;
import com.abbink.simplewebstack.common.metrics.timing.TimingResourceMethodDispatchAdapter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class MetricsModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(MetricRegistry.class).in(Scopes.SINGLETON);
		
		bind(TimingResourceMethodDispatchAdapter.class);
		bind(HttpStatusCodeMetricResourceFilterFactory.class);
	}
	
}
