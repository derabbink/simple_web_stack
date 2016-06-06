package com.abbink.simplewebstack.common.auth.shiro;

import static com.abbink.simplewebstack.common.data.generated.Tables.ACCESS_TOKENS;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
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
		BearerTokenAuthenticationToken bearerToken = (BearerTokenAuthenticationToken)token;
		BearerTokenAuthenticationInfo user = getUser((String) bearerToken.getPrincipal());
		
		if (
			user == null ||
			(user != null && user.isCredentialsExpired())
		) {
			throw new ExpiredCredentialsException("No valid access token provided.");
		}
		
		return user;
	}
	
	private BearerTokenAuthenticationInfo getUser(String tokenSpecificPrincipal) {
		if (tokenSpecificPrincipal == null) {
			return null;
		}
		
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			AccessTokens at = dsl.select()
				.from(ACCESS_TOKENS)
				.where(ACCESS_TOKENS.TOKEN_SCOPED_USER_XID.eq(tokenSpecificPrincipal))
				.fetchAnyInto(AccessTokens.class);
			if (at == null) {
				return null;
			}
			
			byte[] storedSalt = CodecSupport.toBytes(at.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			return new BearerTokenAuthenticationInfo(
				at.getUserId(),
				at.getTokenScopedUserXid(),
				at.getToken(),
				salt,
				at.getExpiresAt() == null ? null : at.getExpiresAt().toInstant(),
				getName()
			);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
