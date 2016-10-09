package com.abbink.simplewebstack.common.auth.shiro;

import static com.abbink.simplewebstack.data.generated.Tables.ACCESS_TOKENS;
import static com.abbink.simplewebstack.data.generated.Tables.APP_SCOPED_IDS;
import static com.abbink.simplewebstack.data.generated.Tables.USERS;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

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
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.abbink.simplewebstack.common.auth.shiro.authtokens.BearerTokenAuthenticationToken;
import com.abbink.simplewebstack.common.auth.shiro.principals.AppScopedXID;
import com.abbink.simplewebstack.common.auth.shiro.principals.CanonicalXID;
import com.abbink.simplewebstack.common.auth.shiro.principals.Principal;
import com.abbink.simplewebstack.common.auth.shiro.principals.UserID;
import com.abbink.simplewebstack.data.generated.tables.pojos.AccessTokens;
import com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds;
import com.abbink.simplewebstack.data.generated.tables.pojos.Users;
import com.google.common.collect.Lists;

@Slf4j
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
			Record r = dsl.select()
				.from(ACCESS_TOKENS)
				.join(APP_SCOPED_IDS).on(ACCESS_TOKENS.APP_SCOPED_USER_XID.eq(APP_SCOPED_IDS.APP_SCOPED_USER_XID))
				.join(USERS).on(APP_SCOPED_IDS.USER_ID.eq(USERS.ID))
				.where(ACCESS_TOKENS.TOKEN_SCOPED_USER_XID.eq((String) bearerToken.getPrincipal()))
				.fetchAny();
			AccessTokens at = r.into(AccessTokens.class);
			AppScopedIds asId = r.into(AppScopedIds.class);
			Users u = r.into(Users.class);
			if (at == null || asId == null || u == null) {
				return null;
			}
			if (at.getExpiresAt().toInstant().isBefore(Instant.now())) {
				throw new ExpiredCredentialsException("No valid access token provided.");
			}
			
			byte[] storedSalt = CodecSupport.toBytes(at.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			return new SimpleAuthenticationInfo(
				Lists.<Principal<?>>newArrayList(
					new AppScopedXID(at.getAppScopedUserXid()),
					new CanonicalXID(u.getXid()),
					new UserID(u.getId())
				),
				at.getToken(),
				salt,
				getName()
			);
		} catch (SQLException e) {
			log.error("Unable to read access_token from DB", e);
		}
		
		return null;
	}
}
