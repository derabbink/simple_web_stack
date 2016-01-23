package com.abbink.simplewebstack.common.auth.di;

import javax.servlet.ServletContext;

import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.web.mgt.WebSecurityManager;

import com.google.inject.binder.AnnotatedBindingBuilder;

/**
 * Required to prevent binding clashes resulting from repeated installations of {@link ShiroWebModule}s.
 */
abstract public class ReusableShiroWebModule extends ShiroWebModule {
	private static boolean webSecurityManagerIsInstalled = false;
	
	public ReusableShiroWebModule(ServletContext servletContext) {
		super(servletContext);
	}
	
	@Override
	protected void bindWebSecurityManager(AnnotatedBindingBuilder<? super WebSecurityManager> bind) {
		if (webSecurityManagerIsInstalled) {
			return;
		}
		System.out.println("ARGH!!!!!!!!!");
		super.bindWebSecurityManager(bind);
		webSecurityManagerIsInstalled = true;
	}
}
