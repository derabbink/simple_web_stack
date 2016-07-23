package com.abbink.simplewebstack.common.auth.shiro;

import static com.abbink.simplewebstack.common.data.generated.Tables.REMEMBER_ME_TOKENS;
import static com.abbink.simplewebstack.common.data.generated.Tables.SESSIONS;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.abbink.simplewebstack.common.auth.shiro.authtokens.RememberMeAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.authtokens.SessionAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.authtokens.WebAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.principals.ExternalID;
import com.abbink.simplewebstack.common.auth.shiro.principals.RememberMeToken;
import com.abbink.simplewebstack.common.auth.shiro.principals.SessionID;
import com.abbink.simplewebstack.common.data.generated.tables.pojos.RememberMeTokens;
import com.abbink.simplewebstack.common.data.generated.tables.pojos.Sessions;

public class WebAuthenticationRealm extends AuthenticatingRealm {
	
	private JdbcDataSource ds;
	private SQLDialect dialect;
	
	@Inject
	public WebAuthenticationRealm(JdbcDataSource ds, SQLDialect dialect) {
		this.ds = ds;
		this.dialect = dialect;
		
		setAuthenticationTokenClass(WebAuthenticationToken.class);
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
		credentialsMatcher.setStoredCredentialsHexEncoded(false); // we use base64, not hex
		credentialsMatcher.setHashIterations(1024);
		setCredentialsMatcher(credentialsMatcher);
	}
	
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if (token instanceof SessionAuthenticationToken) {
			SessionAuthenticationToken st = (SessionAuthenticationToken) token;
			return getUserFromSessionId((String) st.getPrincipal());
		} else if (token instanceof RememberMeAuthenticationToken) {
			RememberMeAuthenticationToken rmt = (RememberMeAuthenticationToken) token;
			return getUserFromRememberMeToken((String) rmt.getPrincipal());
		}
		return null;
	}
	
	private AuthenticationInfo getUserFromSessionId(String sessionId) {
		if (sessionId == null) {
			return null;
		}
		
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			Sessions s = dsl.select()
				.from(SESSIONS)
				.where(SESSIONS.ID.eq(sessionId))
				.fetchAnyInto(Sessions.class);
			if (s == null) {
				return null;
			}
			
			if (s.getExpiresAt().toInstant().isBefore(Instant.now())) {
				throw new ExpiredCredentialsException();
			}
			
			byte[] storedSalt = CodecSupport.toBytes(s.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			SimplePrincipalCollection principals = new SimplePrincipalCollection();
			principals.add(new ExternalID(s.getUserXid()), getName());
			principals.add(new SessionID(sessionId), getName());
			return new SimpleAuthenticationInfo(
				principals,
				s.getCredentials(),
				salt,
				getName()
			);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private AuthenticationInfo getUserFromRememberMeToken(String rememberMeToken) {
		if (rememberMeToken == null) {
			return null;
		}
		
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			RememberMeTokens rmt = dsl.select()
				.from(REMEMBER_ME_TOKENS)
				.where(REMEMBER_ME_TOKENS.TOKEN.eq(rememberMeToken))
				.fetchAnyInto(RememberMeTokens.class);
			if (rmt == null) {
				return null;
			}
			
			if (rmt.getExpiresAt().toInstant().isBefore(Instant.now())) {
				throw new ExpiredCredentialsException();
			}
			
			byte[] storedSalt = CodecSupport.toBytes(rmt.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			SimplePrincipalCollection principals = new SimplePrincipalCollection();
			principals.add(new ExternalID(rmt.getUserXid()), getName());
			principals.add(new RememberMeToken(rememberMeToken), getName());
			return new SimpleAuthenticationInfo(
				principals,
				rmt.getCredentials(),
				salt,
				getName()
			);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void endSessionId(SessionID sessionId) {
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			dsl.update(SESSIONS)
				.set(SESSIONS.EXPIRES_AT, Timestamp.from(Instant.now()))
				.where(SESSIONS.ID.eq(sessionId.getValue()))
				.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endRememberMeToken(RememberMeToken rememberMeToken) {
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			dsl.update(REMEMBER_ME_TOKENS)
				.set(REMEMBER_ME_TOKENS.EXPIRES_AT, Timestamp.from(Instant.now()))
				.where(REMEMBER_ME_TOKENS.TOKEN.eq(rememberMeToken.getValue()))
				.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertSessionToken(
		Hash id,
		Hash hashedToken,
		ByteSource salt,
		String userXId,
		Instant expriyTime
	) {
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			dsl.insertInto(SESSIONS, SESSIONS.ID, SESSIONS.CREDENTIALS, SESSIONS.SALT, SESSIONS.USER_XID, SESSIONS.EXPIRES_AT)
				.values(id.toBase64(), hashedToken.toBase64(), salt.toBase64(), userXId, Timestamp.from(expriyTime))
				.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertRememberMeToken(
		String token,
		Hash hashedCredentials,
		ByteSource salt,
		String userXId,
		Instant expriyTime
	) {
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			dsl.insertInto(REMEMBER_ME_TOKENS,
					REMEMBER_ME_TOKENS.TOKEN, REMEMBER_ME_TOKENS.CREDENTIALS, REMEMBER_ME_TOKENS.SALT,
					REMEMBER_ME_TOKENS.USER_XID, REMEMBER_ME_TOKENS.EXPIRES_AT)
				.values(token, hashedCredentials.toBase64(), salt.toBase64(), userXId, Timestamp.from(expriyTime))
				.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateSessionTokenExpiryTime(
		Hash id,
		Instant expriyTime
	) {
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			dsl.update(SESSIONS)
				.set(SESSIONS.EXPIRES_AT, Timestamp.from(expriyTime))
				.where(SESSIONS.ID.eq(id.toBase64()))
				.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateRememberMeTokenExpiryTime(
			String token,
		Instant expriyTime
	) {
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			dsl.update(REMEMBER_ME_TOKENS)
				.set(REMEMBER_ME_TOKENS.EXPIRES_AT, Timestamp.from(expriyTime))
				.where(REMEMBER_ME_TOKENS.TOKEN.eq(token))
				.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
