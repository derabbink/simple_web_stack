package com.abbink.simplewebstack.common.auth.mechanisms.di;

import com.abbink.simplewebstack.common.auth.mechanisms.AnonymousAuthenticationMechanism2;
import com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.BearerTokenAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.OptionalWebAuthenticationMechanism2;
import com.abbink.simplewebstack.common.auth.mechanisms.RejectAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.mechanisms.WebAuthenticationMechanism2;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryProvider;
import com.google.inject.multibindings.MapBinder;

public class AuthenticationMechanismModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind authentication mechanisms
		MapBinder<Class<? extends AuthenticationMechanism>, AuthenticationMechanism.Factory> mapBinder = MapBinder.newMapBinder(
			binder(),
			new TypeLiteral<Class<? extends AuthenticationMechanism>>() {},
			new TypeLiteral<AuthenticationMechanism.Factory>() {}
		);
		// There is no assisted inject available for multibindings yet, so we keep using the deprecated FactoryProvider
		mapBinder.addBinding(AnonymousAuthenticationMechanism2.class).toProvider(FactoryProvider.newFactory(
			AuthenticationMechanism.Factory.class, AnonymousAuthenticationMechanism2.class));
		mapBinder.addBinding(RejectAuthenticationMechanism.class).toProvider(FactoryProvider.newFactory(
			AuthenticationMechanism.Factory.class, RejectAuthenticationMechanism.class));
		mapBinder.addBinding(BearerTokenAuthenticationMechanism.class).toProvider(FactoryProvider.newFactory(
			AuthenticationMechanism.Factory.class, BearerTokenAuthenticationMechanism.class));
		mapBinder.addBinding(WebAuthenticationMechanism2.class).toProvider(FactoryProvider.newFactory(
			AuthenticationMechanism.Factory.class, WebAuthenticationMechanism2.class));
		mapBinder.addBinding(OptionalWebAuthenticationMechanism2.class).toProvider(FactoryProvider.newFactory(
			AuthenticationMechanism.Factory.class, OptionalWebAuthenticationMechanism2.class));
	}

}
