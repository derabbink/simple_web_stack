package com.abbink.simplewebstack.common.auth.jersey;

import java.util.List;

import javax.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import com.abbink.simplewebstack.common.auth.jersey.aop.OverrideInputType;
import com.google.common.collect.Lists;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.api.model.AbstractSubResourceLocator;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

@Singleton
@Slf4j
public class OverrideInputTypeResourceFilterFactory implements ResourceFilterFactory {
	
	@Override
	public List<ResourceFilter> create(AbstractMethod am) {
		// documented to only be AbstractSubResourceLocator, AbstractResourceMethod, or AbstractSubResourceMethod
		if (am instanceof AbstractSubResourceLocator) {
			// not actually invoked per request, nothing to do
			log.trace("Ignoring AbstractSubResourceLocator {}", am);
			return null;
		} else if (am instanceof AbstractResourceMethod) {
			OverrideInputType annotation = am.getAnnotation(OverrideInputType.class);
			if (annotation == null) {
				annotation = am.getResource().getAnnotation(OverrideInputType.class);
			}
			if (annotation != null) {
				return Lists.<ResourceFilter>newArrayList(
					new OverrideInputTypeResourceFilter(annotation.value(), annotation.except()));
			}
		} else {
			log.warn("Got an unexpected instance of {}: {}", am.getClass().getName(), am);
		}
		return null;
	}
	
}
