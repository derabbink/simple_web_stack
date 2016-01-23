package com.abbink.simplewebstack.common.jersey.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;

public class SubjectInjectable extends AbstractHttpContextInjectable<Subject> {

	@Override
	public Subject getValue(HttpContext c) {
		return SecurityUtils.getSubject();
	}

}
