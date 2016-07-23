package com.abbink.simplewebstack.common.auth.shiro.authtokens;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 * Shiro assumes you know the principal when looking up corresponding credentials. You cannot look up the principal for a given
 * credential string.
 * That's why a remember-me authentication token (cookie value) consists of two parts: The Principal, and the Credentials.
 * The whole token is a query string containing these two as key/value pairs: p=abc&c=xyz
 */
public class RememberMeAuthenticationToken extends WebAuthenticationToken {
	public static String PRINCIPAL_KEY = "p";
	public static String CREDENTIALS_KEY = "c";
	
	private String rememberMeToken;
	
	public RememberMeAuthenticationToken(String rememberMeToken) {
		this.rememberMeToken = rememberMeToken;
	}
	
	@Override
	public Object getPrincipal() {
		List<NameValuePair> elements = URLEncodedUtils.parse(rememberMeToken, Charset.defaultCharset());
		for (NameValuePair element : elements) {
			if (PRINCIPAL_KEY.equals(element.getName())) {
				return element.getValue();
			}
		}
		return null;
	}
	
	@Override
	public Object getCredentials() {
		List<NameValuePair> elements = URLEncodedUtils.parse(rememberMeToken, Charset.defaultCharset());
		for (NameValuePair element : elements) {
			if (CREDENTIALS_KEY.equals(element.getName())) {
				return element.getValue();
			}
		}
		return null;
	}
}
