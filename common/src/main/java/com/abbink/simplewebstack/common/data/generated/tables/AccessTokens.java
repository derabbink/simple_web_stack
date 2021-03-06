/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated.tables;


import com.abbink.simplewebstack.common.data.generated.Keys;
import com.abbink.simplewebstack.common.data.generated.Public;
import com.abbink.simplewebstack.common.data.generated.tables.records.AccessTokensRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class AccessTokens extends TableImpl<AccessTokensRecord> {

	private static final long serialVersionUID = -1502361054;

	/**
	 * The reference instance of <code>PUBLIC.ACCESS_TOKENS</code>
	 */
	public static final AccessTokens ACCESS_TOKENS = new AccessTokens();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AccessTokensRecord> getRecordType() {
		return AccessTokensRecord.class;
	}

	/**
	 * The column <code>PUBLIC.ACCESS_TOKENS.ID</code>.
	 */
	public final TableField<AccessTokensRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>PUBLIC.ACCESS_TOKENS.USER_ID</code>.
	 */
	public final TableField<AccessTokensRecord, Integer> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCESS_TOKENS.APP_ID</code>.
	 */
	public final TableField<AccessTokensRecord, Integer> APP_ID = createField("APP_ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCESS_TOKENS.TOKEN_SCOPED_USER_XID</code>.
	 */
	public final TableField<AccessTokensRecord, String> TOKEN_SCOPED_USER_XID = createField("TOKEN_SCOPED_USER_XID", org.jooq.impl.SQLDataType.CHAR.length(10).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCESS_TOKENS.SALT</code>.
	 */
	public final TableField<AccessTokensRecord, String> SALT = createField("SALT", org.jooq.impl.SQLDataType.CHAR.length(24).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCESS_TOKENS.TOKEN</code>.
	 */
	public final TableField<AccessTokensRecord, String> TOKEN = createField("TOKEN", org.jooq.impl.SQLDataType.CHAR.length(88).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.ACCESS_TOKENS.EXPIRES_AT</code>.
	 */
	public final TableField<AccessTokensRecord, Timestamp> EXPIRES_AT = createField("EXPIRES_AT", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * Create a <code>PUBLIC.ACCESS_TOKENS</code> table reference
	 */
	public AccessTokens() {
		this("ACCESS_TOKENS", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.ACCESS_TOKENS</code> table reference
	 */
	public AccessTokens(String alias) {
		this(alias, ACCESS_TOKENS);
	}

	private AccessTokens(String alias, Table<AccessTokensRecord> aliased) {
		this(alias, aliased, null);
	}

	private AccessTokens(String alias, Table<AccessTokensRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<AccessTokensRecord, Integer> getIdentity() {
		return Keys.IDENTITY_ACCESS_TOKENS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AccessTokensRecord> getPrimaryKey() {
		return Keys.CONSTRAINT_5;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AccessTokensRecord>> getKeys() {
		return Arrays.<UniqueKey<AccessTokensRecord>>asList(Keys.CONSTRAINT_5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccessTokens as(String alias) {
		return new AccessTokens(alias, this);
	}

	/**
	 * Rename this table
	 */
	public AccessTokens rename(String name) {
		return new AccessTokens(name, null);
	}
}
