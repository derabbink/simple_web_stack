package com.abbink.simplewebstack.common.auth.service;

import static java.lang.Math.toIntExact;

import java.nio.charset.Charset;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

import com.abbink.simplewebstack.common.auth.mechanisms.WebAuthenticationMechanism;
import com.abbink.simplewebstack.common.auth.shiro.UsernamePasswordSecurityManager;
import com.abbink.simplewebstack.common.auth.shiro.WebAuthenticationRealm;
import com.abbink.simplewebstack.common.auth.shiro.authtokens.RememberMeAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.principals.ExternalID;
import com.abbink.simplewebstack.common.auth.utils.RandomStringGenerator;

public class WebLoginService {
	
	private static final int SESSION_TOKEN_LENGTH = 100;
	private static final int REMEMBER_ME_TOKEN_LENGTH = 10;
	private static final int REMEMBER_ME_CREDENTIALS_LENGTH = 100;
	private static final int SALT_LENGTH = 16;
	private static final int REMEMBER_ME_EXPIRY_TIME = 30; // days
	
	private UsernamePasswordSecurityManager usernamePasswordSecurityManager;
	private WebAuthenticationRealm realm;
	private RandomNumberGenerator numberGenerator;
	private RandomStringGenerator stringGenerator;
	
	@Inject
	public WebLoginService(
		UsernamePasswordSecurityManager usernamePasswordSecurityManager,
		WebAuthenticationRealm realm,
		RandomNumberGenerator numberGenerator,
		RandomStringGenerator stringGenerator
	) {
		this.usernamePasswordSecurityManager = usernamePasswordSecurityManager;
		this.realm = realm;
		this.numberGenerator = numberGenerator;
		this.stringGenerator = stringGenerator;
	}
	
	public boolean tryLogin(
		String username,
		String password,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		WebSessionContext sessionContext = new DefaultWebSessionContext();
		sessionContext.setServletRequest(request);
		sessionContext.setServletResponse(response);
		Session oldSession = usernamePasswordSecurityManager.getSession((SessionKey)sessionContext);
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		WebSubjectContext context = new DefaultWebSubjectContext();
		context.setServletRequest(request);
		context.setServletResponse(response);
		context.setSessionCreationEnabled(true);
		Subject subject = usernamePasswordSecurityManager.createSubject(context);
		try {
			subject.login(token);
			Object principal = subject.getPrincipal();
			ExternalID userXId = null;
			if (principal instanceof ExternalID) {
				userXId = (ExternalID) principal;
			}
			if (userXId == null) {
				return false;
			}
			
			initSession(userXId.getValue(), request, response, oldSession, subject.getSession());
			return true;
		} catch (AuthenticationException e) {
			return false;
		}
	}
	
	private void initSession(
		String userXId,
		HttpServletRequest request,
		HttpServletResponse response,
		Session oldSession,
		Session newSession
	) {
		if (oldSession != null) {
			oldSession.stop();
		}
		
		Hash id = new Sha512Hash(ByteSource.Util.bytes(newSession.getId()));
		ByteSource sessionToken = numberGenerator.nextBytes(SESSION_TOKEN_LENGTH);
		ByteSource salt = numberGenerator.nextBytes(SALT_LENGTH);
		Hash hashedCredentials = new Sha512Hash(sessionToken, salt, 1024);
		Instant expriyTime = newSession.getLastAccessTime().toInstant()
			.plus(newSession.getTimeout(), ChronoUnit.MILLIS);
		realm.insertSessionToken(id, hashedCredentials, salt, userXId, expriyTime);
		newSession.setAttribute(WebAuthenticationMechanism.SESSION_CREDENTIALS, sessionToken.toBase64());
		
		String rememberMeToken = stringGenerator.getRandomString(REMEMBER_ME_TOKEN_LENGTH);
		String rememberMeCredentials = stringGenerator.getRandomString(REMEMBER_ME_CREDENTIALS_LENGTH);
		salt = numberGenerator.nextBytes(SALT_LENGTH);
		hashedCredentials = new Sha512Hash(ByteSource.Util.bytes(rememberMeCredentials), salt, 1024);
		expriyTime = Instant.now().plus(REMEMBER_ME_EXPIRY_TIME, ChronoUnit.DAYS);
		realm.insertRememberMeToken(rememberMeToken, hashedCredentials, salt, userXId, expriyTime);
		List<NameValuePair> cookieParts = new ArrayList<NameValuePair>();
		cookieParts.add(new BasicNameValuePair(RememberMeAuthenticationToken.PRINCIPAL_KEY, rememberMeToken));
		cookieParts.add(new BasicNameValuePair(RememberMeAuthenticationToken.CREDENTIALS_KEY, rememberMeCredentials));
		String rememberMeCookieValue = URLEncodedUtils.format(cookieParts, Charset.defaultCharset());
		Cookie rememberMeCookie = new Cookie(WebAuthenticationMechanism.REMEMBER_ME_COOKIE, rememberMeCookieValue);
		rememberMeCookie.setMaxAge(toIntExact(Duration.ofDays(REMEMBER_ME_EXPIRY_TIME).getSeconds()));
		response.addCookie(rememberMeCookie);
	}
	
	/**
	 * This will only work for subjects that originate from the WebSecurityManager
	 */
	public void logout(
		Subject subject,
		HttpServletResponse response
	) {
		subject.logout();
		subject.getSession().stop();
		
	}
}
