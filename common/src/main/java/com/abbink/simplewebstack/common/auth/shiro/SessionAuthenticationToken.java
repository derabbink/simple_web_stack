package com.abbink.simplewebstack.common.auth.shiro;

import com.abbink.simplewebstack.common.auth.mechanisms.FormAuthenticationMechanism;

/**
 * This AuthenticationToken is used for when an active session is present. The session ID serves as the principal
 * and token stored in {@link FormAuthenticationMechanism#SESSION_CREDENTIALS} is used to verify the legitimacy/validity of a session.
 * Instead of storing the active subject's user ID in the session, the session ID is used to look it up - only if its valid.
 */
public class SessionAuthenticationToken extends WebAuthenticationToken {
	
	private String sessionId;
	private String credentials;
	
	public SessionAuthenticationToken(String sessionId, String credentials) {
		this.sessionId = sessionId;
		this.credentials = credentials;
	}
	
	@Override
	public Object getPrincipal() {
		return sessionId;
	}
	
	@Override
	public Object getCredentials() {
		return credentials;
	}
}
