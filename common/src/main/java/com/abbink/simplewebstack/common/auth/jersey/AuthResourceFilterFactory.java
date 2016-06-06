package com.abbink.simplewebstack.common.auth.jersey;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.extern.java.Log;

import com.abbink.simplewebstack.common.aop.Auth;
import com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.api.model.AbstractSubResourceLocator;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

@Singleton
@Log
public class AuthResourceFilterFactory implements ResourceFilterFactory {

	private Map<Class<? extends AuthenticationMechanism>, AuthenticationMechanism> authenticationMechanisms;
	
	@Inject
	public AuthResourceFilterFactory(Map<Class<? extends AuthenticationMechanism>, AuthenticationMechanism> authenticationMechanisms) {
		this.authenticationMechanisms = authenticationMechanisms;
	}
	
	@Override
	public List<ResourceFilter> create(AbstractMethod am) {
		// documented to only be AbstractSubResourceLocator, AbstractResourceMethod, or AbstractSubResourceMethod
		if (am instanceof AbstractSubResourceLocator) {
			// not actually invoked per request, nothing to do
			log.info("Ignoring AbstractSubResourceLocator " + am);
			return null;
		} else if (am instanceof AbstractResourceMethod) {
			Auth annotation = am.getAnnotation(Auth.class);
			if (annotation == null) {
				annotation = am.getResource().getAnnotation(Auth.class);
			}
			if (annotation != null) {
				Class<? extends AuthenticationMechanism> m = annotation.value();
				AuthenticationMechanism mechanism = authenticationMechanisms.get(m);
				if (mechanism != null) {
					return Lists.<ResourceFilter>newArrayList(mechanism);
				}
				log.severe("Could not find AuthenticationMechanism "+ m.getName() +" for "+ am.getClass().getName() + ": " + am);
			}
		} else {
			log.warning("Got an unexpected instance of " + am.getClass().getName() + ": " + am);
		}
		return null;
	}

}
