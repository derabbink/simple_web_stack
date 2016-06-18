package com.abbink.simplewebstack.common.auth.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import com.google.common.base.Strings;

/**
 * Shiro assumes you know the principal when looking up corresponding credentials. You cannot look up the principal for a given
 * credential string.
 * That's why access tokens are split in two: a Token ID, followed by the Credentials. Concatenated, they form the bearer token.
 * The Token ID has length {@link #TOKEN_ID_LENGTH}; all characters following it are considered to be the credentials.
 */
public class BearerTokenAuthenticationToken implements AuthenticationToken {
	public static final int TOKEN_ID_LENGTH = 10;
	
	private String bearerToken;
	
	public BearerTokenAuthenticationToken(String bearerToken) {
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

