package com.abbink.simplewebstack.common.auth.utils;

import org.apache.shiro.subject.Subject;

import com.abbink.simplewebstack.common.auth.shiro.principals.AppScopedXID;
import com.abbink.simplewebstack.common.auth.shiro.principals.CanonicalXID;
import com.abbink.simplewebstack.common.auth.shiro.principals.UserID;
import com.google.common.collect.Iterables;

public abstract class SubjectUtils {
	
	/**
	 * Utils class. should never be instantiated.
	 */
	private SubjectUtils() {}
	
	@SuppressWarnings("unchecked")
	private static <T> T getPrincipal(Class<T> principalType, Subject sub) {
		return (T) Iterables.getFirst(
			Iterables.filter(sub.getPrincipals(), c -> principalType.isAssignableFrom(c.getClass())),
			null
		);
	}
	
	public static AppScopedXID getAppScopedXID(Subject sub) {
		return getPrincipal(AppScopedXID.class, sub);
	}
	
	public static String getAppScopedXIDValue(Subject sub) {
		AppScopedXID principal = getAppScopedXID(sub);
		if (principal != null) {
			return principal.getValue();
		}
		return null;
	}
	
	public static CanonicalXID getCanonicalXID(Subject sub) {
		return getPrincipal(CanonicalXID.class, sub);
	}
	
	public static String getCanonicalXIDValue(Subject sub) {
		CanonicalXID principal = getCanonicalXID(sub);
		if (principal != null) {
			return principal.getValue();
		}
		return null;
	}
	
	public static UserID getUserID(Subject sub) {
		return getPrincipal(UserID.class, sub);
	}
	
	public static Integer getUserIDValue(Subject sub) {
		UserID principal = getUserID(sub);
		if (principal != null) {
			return principal.getValue();
		}
		return null;
	}
}
