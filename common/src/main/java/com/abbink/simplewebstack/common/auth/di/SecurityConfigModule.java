package com.abbink.simplewebstack.common.auth.di;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import com.abbink.simplewebstack.common.auth.RandomStringGenerator;
import com.google.inject.AbstractModule;

public class SecurityConfigModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(RandomNumberGenerator.class).to(SecureRandomNumberGenerator.class);
		bind(RandomStringGenerator.class);
	}
}
