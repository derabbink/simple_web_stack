package com.abbink.simplewebstack.common.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AuthInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String name = invocation.getMethod().getName();
		if (!name.equals("getStats")) {
			throw new IllegalStateException(name + " intercepted!");
		}
		return invocation.proceed();
	}

}
