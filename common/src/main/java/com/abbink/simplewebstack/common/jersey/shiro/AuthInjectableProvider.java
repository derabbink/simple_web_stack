package com.abbink.simplewebstack.common.jersey.shiro;

import javax.ws.rs.ext.Provider;

import org.apache.shiro.subject.Subject;

import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

/**
 * This is very similar to org.secnod.shiro:jersey-shiro:0.1.0, but that doesn't work
 */
@Provider
public class AuthInjectableProvider implements InjectableProvider<Auth, Parameter> {
	
	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}
	
	@Override
	public Injectable<Subject> getInjectable(ComponentContext cc, Auth a, Parameter p) {
		return new SubjectInjectable();
	}
}
