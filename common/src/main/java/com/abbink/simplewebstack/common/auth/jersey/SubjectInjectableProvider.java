package com.abbink.simplewebstack.common.auth.jersey;

import static com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism.SUBJECT_KEY;

import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.subject.Subject;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

@Singleton
@Provider
public class SubjectInjectableProvider
	extends AbstractHttpContextInjectable<Subject>
	implements InjectableProvider<Context, Parameter> {
	
	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}
	
	@Override
	public Injectable<Subject> getInjectable(ComponentContext cc, Context a, Parameter p) {
		if (p.getParameterType().equals(Subject.class)) {
			return this;
		}
		
		return null;
	}
	
	@Override
	public Subject getValue(HttpContext c) {
		// Get subject from HttpServletRequest. Don't use ThreadContext or SecurityUtils
		Object sub = c.getProperties().get(SUBJECT_KEY);
		if (sub != null && sub instanceof Subject) {
			return (Subject) sub;
		}
		return null;
	}
}
