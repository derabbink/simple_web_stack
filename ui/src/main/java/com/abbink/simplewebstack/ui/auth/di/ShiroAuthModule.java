package com.abbink.simplewebstack.ui.auth.di;

import javax.servlet.ServletContext;

import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.Realm;

import com.abbink.simplewebstack.common.auth.FormAuthenticationFilter;
import com.abbink.simplewebstack.common.auth.UsernamePasswordRealm;
import com.google.inject.Key;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

public class ShiroAuthModule extends ShiroWebModule {// AbstractModule
	
	public static final Key<FormAuthenticationFilter> FORM = Key.get(FormAuthenticationFilter.class);
	
	public ShiroAuthModule(ServletContext servletContext) {
		super(servletContext);
	}
	
//	@Override
//	protected void configure() {
//		configureShiroWeb();
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void configureShiroWeb() {
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_usernameParam")).to("username");
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_passwordParam")).to("password");
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_loginUrl")).to("/login");
		bindConstant().annotatedWith(Names.named("FormAuthenticationFilter_successUrl")).to("/login");
		
		bindRealm().to(UsernamePasswordRealm.class);
		
		addFilterChain("/**", FORM);
	}
	
//	/**
//	 * This is a clone of {@link ShiroWebModule#bindRealm()}
//	 */
//	protected final LinkedBindingBuilder<Realm> bindRealm() {
//		Multibinder<Realm> multibinder = Multibinder.newSetBinder(binder(), Realm.class);
//		return multibinder.addBinding();
//	}
}
