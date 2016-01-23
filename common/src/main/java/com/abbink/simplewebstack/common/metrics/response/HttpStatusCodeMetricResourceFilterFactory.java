package com.abbink.simplewebstack.common.metrics.response;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;

import com.codahale.metrics.MetricRegistry;
import com.google.common.collect.Lists;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.api.model.AbstractSubResourceLocator;
import com.sun.jersey.api.model.AbstractSubResourceMethod;
import com.sun.jersey.api.model.PathValue;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

@Singleton
@Log
public final class HttpStatusCodeMetricResourceFilterFactory implements ResourceFilterFactory {
	private final MetricRegistry metricRegistry;
	
	@Inject
	HttpStatusCodeMetricResourceFilterFactory(MetricRegistry metricRegistry) {
		this.metricRegistry = metricRegistry;
	}
	
	public List<ResourceFilter> create(AbstractMethod am) {
		// documented to only be AbstractSubResourceLocator, AbstractResourceMethod, or AbstractSubResourceMethod
		if (am instanceof AbstractSubResourceLocator) {
			// not actually invoked per request, nothing to do
			log.info("Ignoring AbstractSubResourceLocator " + am);
			return null;
		} else if (am instanceof AbstractResourceMethod) {
			String metricBaseName = getMetricBaseName((AbstractResourceMethod) am);
			Class<?> resourceClass = am.getResource().getResourceClass();
			
			return Lists.<ResourceFilter>newArrayList(
				new HttpStatusCodeMetricResourceFilter(metricRegistry, metricBaseName, resourceClass));
		} else {
			log.warning("Got an unexpected instance of " + am.getClass().getName() + ": " + am);
			return null;
		}
	}
	
	static String getMetricBaseName(AbstractResourceMethod am) {
		String metricId = getPathWithoutSurroundingSlashes(am.getResource().getPath());
		
		if (!metricId.isEmpty()) {
			metricId = "/" + metricId;
		}
		
		String httpMethod;
		if (am instanceof AbstractSubResourceMethod) {
			// if this is a subresource, add on the subresource's path component
			AbstractSubResourceMethod asrm = (AbstractSubResourceMethod) am;
			metricId += "/" + getPathWithoutSurroundingSlashes(asrm.getPath());
			httpMethod = asrm.getHttpMethod();
		} else {
			httpMethod = am.getHttpMethod();
		}
		
		if (metricId.isEmpty()) {
			// this happens for WadlResource -- that case actually exists at "application.wadl" though
			metricId = "(no path)";
		}
		
		metricId += "api " + httpMethod + " " + metricId;
		
		return metricId;
	}
	
	private static String getPathWithoutSurroundingSlashes(PathValue pathValue) {
		if (pathValue == null) {
			return "";
		}
		String value = pathValue.getValue();
		if (value.startsWith("/")) {
			value = value.substring(1);
		}
		if (value.endsWith("/")) {
			value = value.substring(0, value.length() - 1);
		}
		
		return value;
	}
}
