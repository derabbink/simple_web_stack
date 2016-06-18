package com.abbink.simplewebstack.common.auth.shiro;

import static com.abbink.simplewebstack.common.data.generated.Tables.REMEMBER_ME_TOKENS;
import static com.abbink.simplewebstack.common.data.generated.Tables.SESSIONS;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.abbink.simplewebstack.common.data.generated.tables.pojos.RememberMeTokens;
import com.abbink.simplewebstack.common.data.generated.tables.pojos.Sessions;

public class FormAuthenticationRealm extends AuthenticatingRealm {
	
	private JdbcDataSource ds;
	private SQLDialect dialect;
	
	@Inject
	public FormAuthenticationRealm(JdbcDataSource ds, SQLDialect dialect) {
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
		} else {
			RememberMeAuthenticationToken rmt = (RememberMeAuthenticationToken) token;
			return getUserFromRememberMeToken((String) rmt.getPrincipal());
		}
	}
	
	private SimpleAuthenticationInfo getUserFromSessionId(String sessionId) {
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
			
			byte[] storedSalt = CodecSupport.toBytes(s.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			return new SimpleAuthenticationInfo(
				s.getId(),
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
	
	private SimpleAuthenticationInfo getUserFromRememberMeToken(String rememberMeToken) {
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
			
			byte[] storedSalt = CodecSupport.toBytes(rmt.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			return new SimpleAuthenticationInfo(
				rmt.getToken(),
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
	
}
