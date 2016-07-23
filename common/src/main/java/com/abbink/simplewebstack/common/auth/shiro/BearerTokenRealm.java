package com.abbink.simplewebstack.common.auth.shiro;

import static com.abbink.simplewebstack.common.data.generated.Tables.ACCESS_TOKENS;

import java.sql.Connection;
import java.sql.SQLException;
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
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.abbink.simplewebstack.common.auth.shiro.authtokens.BearerTokenAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.principals.AppScopedExternalID;
import com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens;

public class BearerTokenRealm extends AuthenticatingRealm {
	
	private JdbcDataSource ds;
	private SQLDialect dialect;
	
	@Inject
	public BearerTokenRealm(JdbcDataSource ds, SQLDialect dialect) {
		this.ds = ds;
		this.dialect = dialect;
		
		setAuthenticationTokenClass(BearerTokenAuthenticationToken.class);
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
		credentialsMatcher.setStoredCredentialsHexEncoded(false); // we use base64, not hex
		credentialsMatcher.setHashIterations(1024);
		setCredentialsMatcher(credentialsMatcher);
	}
	
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		BearerTokenAuthenticationToken bearerToken = null;
		if (token instanceof BearerTokenAuthenticationToken) {
			bearerToken = (BearerTokenAuthenticationToken)token;
		} else {
			return null;
		}
		
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			AccessTokens at = dsl.select()
				.from(ACCESS_TOKENS)
				.where(ACCESS_TOKENS.TOKEN_SCOPED_USER_XID.eq((String) bearerToken.getPrincipal()))
				.fetchAnyInto(AccessTokens.class);
			if (at == null) {
				return null;
			}
			
			if (at.getExpiresAt().toInstant().isBefore(Instant.now())) {
				throw new ExpiredCredentialsException("No valid access token provided.");
			}
			
			byte[] storedSalt = CodecSupport.toBytes(at.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			return new SimpleAuthenticationInfo(
				new AppScopedExternalID(at.getAppScopedUserXid()),
				at.getToken(),
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
