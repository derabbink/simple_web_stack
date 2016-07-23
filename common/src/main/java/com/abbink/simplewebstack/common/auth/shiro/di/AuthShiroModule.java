package com.abbink.simplewebstack.common.auth.shiro.di;

import javax.inject.Singleton;

import org.apache.shiro.crypto.AbstractSymmetricCipherService;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;

import com.abbink.simplewebstack.common.auth.shiro.BearerTokenRealm;
import com.abbink.simplewebstack.common.auth.shiro.BearerTokenSecurityManager;
import com.abbink.simplewebstack.common.auth.shiro.UsernamePasswordRealm;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class AuthShiroModule extends AbstractModule {
	
	@Override
	protected void configure() {
		// bind realms
		bind(BearerTokenRealm.class);
		bind(UsernamePasswordRealm.class);
		
		// bind SecurityManagers
		bind(BearerTokenSecurityManager.class);
	}
	
	@Provides @Singleton
	private WebSecurityManager provideWebSecurityManager(
		UsernamePasswordRealm realm,
		RememberMeManager rememberMeManager
	) {
		DefaultWebSecurityManager result = new DefaultWebSecurityManager(realm);
		result.setRememberMeManager(rememberMeManager);
		return result;
	}
	
	@Provides @Singleton
	private RememberMeManager provideRememberMeManager(
		AbstractSymmetricCipherService cipherService
	) {
		CookieRememberMeManager result = new CookieRememberMeManager();
		result.setCipherService(cipherService);
		result.setCipherKey(cipherService.generateNewKey().getEncoded());
		return result;
	}
	
	@Provides @Singleton
	private AbstractSymmetricCipherService provideCipherService() {
		AesCipherService result = new AesCipherService();
		result.setKeySize(256);
		return result;
	}
}
