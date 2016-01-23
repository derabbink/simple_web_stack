package com.abbink.simplewebstack.common.auth;

import java.time.Instant;

import lombok.NonNull;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.util.ByteSource;

import com.google.common.base.Strings;

public class BearerTokenAuthenticationToken implements AuthenticationToken {
	public static final int TOKEN_ID_LENGTH = 10;
	
	private String bearerToken;
	
	public BearerTokenAuthenticationToken(@NonNull String bearerToken) {
		this.bearerToken = bearerToken;
	}

	@Override
	public Object getPrincipal() {
		if (Strings.isNullOrEmpty(bearerToken) || bearerToken.length() < TOKEN_ID_LENGTH+1) {
			return null;
		}
		
		return bearerToken.substring(bearerToken.length()-TOKEN_ID_LENGTH);
	}

	@Override
	public Object getCredentials() {
		if (Strings.isNullOrEmpty(bearerToken) || bearerToken.length() < TOKEN_ID_LENGTH+1) {
			return null;
		}
		return bearerToken.substring(0, TOKEN_ID_LENGTH);
	}
}

