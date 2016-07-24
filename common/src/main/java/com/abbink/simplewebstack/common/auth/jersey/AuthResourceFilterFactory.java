package com.abbink.simplewebstack.common.auth.jersey;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import lombok.extern.slf4j.Slf4j;

import com.abbink.simplewebstack.common.auth.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.api.model.AbstractSubResourceLocator;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

@Singleton
@Slf4j
public class AuthResourceFilterFactory implements ResourceFilterFactory {

	private Map<Class<? extends AuthenticationMechanism>, AuthenticationMechanism.Factory> authenticationMechanismFactories;
	
	@Context private HttpServletRequest servletRequestProxy;
	@Context private HttpServletResponse servletResponseProxy;
	
	@Inject
	public AuthResourceFilterFactory(
		Map<Class<? extends AuthenticationMechanism>, AuthenticationMechanism.Factory> authenticationMechanismFactories
	) {
		this.authenticationMechanismFactories = authenticationMechanismFactories;
	}
	
	@Override
	public List<ResourceFilter> create(AbstractMethod am) {
		// documented to only be AbstractSubResourceLocator, AbstractResourceMethod, or AbstractSubResourceMethod
		if (am instanceof AbstractSubResourceLocator) {
			// not actually invoked per request, nothing to do
			log.debug("Ignoring AbstractSubResourceLocator {}", am);
			return null;
		} else if (am instanceof AbstractResourceMethod) {
			Auth annotation = am.getAnnotation(Auth.class);
			if (annotation == null) {
				annotation = am.getResource().getAnnotation(Auth.class);
			}
			if (annotation != null) {
				Class<? extends AuthenticationMechanism> m = annotation.value();
				AuthenticationMechanism.Factory mechanismFactory = authenticationMechanismFactories.get(m);
				if (mechanismFactory != null) {
					return Lists.<ResourceFilter>newArrayList(mechanismFactory.create(servletRequestProxy, servletResponseProxy));
				}
				log.error("Could not find AuthenticationMechanism {} for {}: {}", m.getName(), am.getClass().getName(), am);
			}
		} else {
			log.warn("Got an unexpected instance of {}: {}", am.getClass().getName(), am);
		}
		return null;
	}

}
