package com.abbink.simplewebstack.common.auth;

import java.time.Instant;

import lombok.NonNull;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class BearerTokenAuthenticationInfo extends SimpleAuthenticationInfo {
	
	private Instant credentialsExpiration;
	private boolean credentialsExpired;
	
	public BearerTokenAuthenticationInfo(
		int uId,
		@NonNull String tokenScopedId,
		@NonNull String hashedBearerToken,
		@NonNull ByteSource tokenSalt,
		Instant credentialsExpiration,
		@NonNull String realmName
	) {
		SimplePrincipalCollection pc = new SimplePrincipalCollection();
		pc.add(uId, realmName);
		pc.add(tokenScopedId, realmName);
		principals = pc;
		credentials = hashedBearerToken;
		credentialsSalt = tokenSalt;
		this.credentialsExpiration = credentialsExpiration;
		credentialsExpired = credentialsExpiration != null && credentialsExpiration.isBefore(Instant.now());
	}
	
	public Instant getCredentialsExpiration() {
		return credentialsExpiration;
	}
	
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}
}
