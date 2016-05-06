package com.abbink.simplewebstack.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.abbink.simplewebstack.common.auth.mechanisms.AuthenticationMechanism;

/**
 * Auth annotation
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	Class<? extends AuthenticationMechanism> value();
}
