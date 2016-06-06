package com.abbink.simplewebstack.common.auth.mechanisms.di;

import com.abbink.simplewebstack.common.auth.mechanisms.AnonymousAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.BearerTokenAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.RejectAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.shiro.di.AuthShiroModule;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

public class AuthenticationMechanismModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AuthShiroModule());
		
		// bind authentication mechanisms
		MapBinder<Class<? extends AuthenticationMechanism>, AuthenticationMechanism> mapBinder = MapBinder.newMapBinder(
			binder(),
			new TypeLiteral<Class<? extends AuthenticationMechanism>>() {},
			new TypeLiteral<AuthenticationMechanism>() {}
		);
		mapBinder.addBinding(AnonymousAuthenticationMechanism.class).to(AnonymousAuthenticationMechanism.class);
		mapBinder.addBinding(RejectAuthenticationMechanism.class).to(RejectAuthenticationMechanism.class);
		mapBinder.addBinding(BearerTokenAuthenticationMechanism.class).to(BearerTokenAuthenticationMechanism.class);
	}

}
