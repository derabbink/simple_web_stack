package com.abbink.simplewebstack.common.auth.shiro;

import static com.abbink.simplewebstack.data.generated.Tables.USERS;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
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

import com.abbink.simplewebstack.common.auth.shiro.principals.AppScopedXID;
import com.abbink.simplewebstack.common.auth.shiro.principals.CanonicalXID;
import com.abbink.simplewebstack.common.auth.shiro.principals.Principal;
import com.abbink.simplewebstack.common.auth.shiro.principals.UserID;
import com.abbink.simplewebstack.common.auth.shiro.principals.UserXID;
import com.abbink.simplewebstack.data.generated.tables.pojos.Users;
import com.google.common.collect.Lists;

@Slf4j
public class UsernamePasswordRealm extends AuthenticatingRealm {
	
	private JdbcDataSource ds;
	private SQLDialect dialect;
	
	@Inject
	public UsernamePasswordRealm(JdbcDataSource ds, SQLDialect dialect) {
		this.ds = ds;
		this.dialect = dialect;
		
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
		credentialsMatcher.setStoredCredentialsHexEncoded(false); // we use base64, not hex
		credentialsMatcher.setHashIterations(1024);
		setCredentialsMatcher(credentialsMatcher);
	}
	
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt;
		if (token instanceof UsernamePasswordToken) {
			upt = (UsernamePasswordToken) token;
		} else {
			return null;
		}
		
		String username = (String) upt.getPrincipal();
		
		try (Connection conn = ds.getConnection()) {
			DSLContext dsl = DSL.using(conn, dialect);
			Users u = dsl.selectFrom(USERS)
				.where(USERS.NAME.eq(username))
				.fetchAnyInto(Users.class);
			if (u == null) {
				return null;
			}
			
			byte[] storedSalt = CodecSupport.toBytes(u.getSalt());
			storedSalt = Base64.decode(storedSalt);
			ByteSource salt = ByteSource.Util.bytes(storedSalt);
			
			return new SimpleAuthenticationInfo(
				Lists.<Principal<?>>newArrayList(
					new CanonicalXID(u.getXid()),
					new UserID(u.getId())
				),
				u.getPassword(),
				salt,
				getName()
			);
		} catch (SQLException e) {
			log.error("Unable to read user from DB", e);
		}
		
		return null;
	}
}
