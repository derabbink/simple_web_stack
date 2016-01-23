/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated.tables.daos;


import com.abbink.simplewebstack.common.data.generated.tables.AccessTokens;
import com.abbink.simplewebstack.common.data.generated.tables.records.AccessTokensRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccessTokensDao extends DAOImpl<AccessTokensRecord, com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens, Integer> {

	/**
	 * Create a new AccessTokensDao without any configuration
	 */
	public AccessTokensDao() {
		super(AccessTokens.ACCESS_TOKENS, com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens.class);
	}

	/**
	 * Create a new AccessTokensDao with an attached configuration
	 */
	public AccessTokensDao(Configuration configuration) {
		super(AccessTokens.ACCESS_TOKENS, com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>ID IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens> fetchById(Integer... values) {
		return fetch(AccessTokens.ACCESS_TOKENS.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>ID = value</code>
	 */
	public com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens fetchOneById(Integer value) {
		return fetchOne(AccessTokens.ACCESS_TOKENS.ID, value);
	}

	/**
	 * Fetch records that have <code>USER_ID IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens> fetchByUserId(Integer... values) {
		return fetch(AccessTokens.ACCESS_TOKENS.USER_ID, values);
	}

	/**
	 * Fetch records that have <code>APP_ID IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens> fetchByAppId(Integer... values) {
		return fetch(AccessTokens.ACCESS_TOKENS.APP_ID, values);
	}

	/**
	 * Fetch records that have <code>TOKEN_SCOPED_USER_XID IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens> fetchByTokenScopedUserXid(String... values) {
		return fetch(AccessTokens.ACCESS_TOKENS.TOKEN_SCOPED_USER_XID, values);
	}

	/**
	 * Fetch records that have <code>SALT IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens> fetchBySalt(String... values) {
		return fetch(AccessTokens.ACCESS_TOKENS.SALT, values);
	}

	/**
	 * Fetch records that have <code>TOKEN IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens> fetchByToken(String... values) {
		return fetch(AccessTokens.ACCESS_TOKENS.TOKEN, values);
	}

	/**
	 * Fetch records that have <code>EXPIRES_AT IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.AccessTokens> fetchByExpiresAt(Timestamp... values) {
		return fetch(AccessTokens.ACCESS_TOKENS.EXPIRES_AT, values);
	}
}
