package com.abbink.simplewebstack.common.auth.di;

import com.abbink.simplewebstack.common.auth.AuthResourceFilterFactory;
import com.abbink.simplewebstack.common.auth.mechanisms.AnonymousAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.RejectAuthenticationMechanism;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

public class AuthModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind authentication mechanisms
		//@SuppressWarnings({ "unchecked", "rawtypes" })
		//Class<Class<? extends AuthenticationMechanism>> key = (Class)Class.class;
//		TypeLiteral<Class<? extends AuthenticationMechanism>> key = new TypeLiteral<Class<? extends AuthenticationMechanism>>() {};
//		TypeLiteral<AuthenticationMechanism> value = new TypeLiteral<AuthenticationMechanism>() {};
		MapBinder<Class<? extends AuthenticationMechanism>, AuthenticationMechanism> mapBinder = MapBinder.newMapBinder(
			binder(),
			new TypeLiteral<Class<? extends AuthenticationMechanism>>() {},
			new TypeLiteral<AuthenticationMechanism>() {}
		);
		mapBinder.addBinding(AnonymousAuthenticationMechanism.class).to(AnonymousAuthenticationMechanism.class);
		mapBinder.addBinding(RejectAuthenticationMechanism.class).to(RejectAuthenticationMechanism.class);
		// add resource method interceptor into jersey
		bind(AuthResourceFilterFactory.class);
	}

}
