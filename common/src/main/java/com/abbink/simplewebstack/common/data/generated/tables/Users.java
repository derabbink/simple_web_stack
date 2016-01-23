/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated.tables;


import com.abbink.simplewebstack.common.data.generated.Keys;
import com.abbink.simplewebstack.common.data.generated.Public;
import com.abbink.simplewebstack.common.data.generated.tables.records.UsersRecord;

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
public class Users extends TableImpl<UsersRecord> {

	private static final long serialVersionUID = -666781015;

	/**
	 * The reference instance of <code>PUBLIC.USERS</code>
	 */
	public static final Users USERS = new Users();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UsersRecord> getRecordType() {
		return UsersRecord.class;
	}

	/**
	 * The column <code>PUBLIC.USERS.ID</code>.
	 */
	public final TableField<UsersRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>PUBLIC.USERS.XID</code>.
	 */
	public final TableField<UsersRecord, String> XID = createField("XID", org.jooq.impl.SQLDataType.CHAR.length(10).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.USERS.NAME</code>.
	 */
	public final TableField<UsersRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.USERS.SALT</code>.
	 */
	public final TableField<UsersRecord, String> SALT = createField("SALT", org.jooq.impl.SQLDataType.CHAR.length(24).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.USERS.PASSWORD</code>.
	 */
	public final TableField<UsersRecord, String> PASSWORD = createField("PASSWORD", org.jooq.impl.SQLDataType.CHAR.length(88).nullable(false), this, "");

	/**
	 * Create a <code>PUBLIC.USERS</code> table reference
	 */
	public Users() {
		this("USERS", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.USERS</code> table reference
	 */
	public Users(String alias) {
		this(alias, USERS);
	}

	private Users(String alias, Table<UsersRecord> aliased) {
		this(alias, aliased, null);
	}

	private Users(String alias, Table<UsersRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<UsersRecord, Integer> getIdentity() {
		return Keys.IDENTITY_USERS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<UsersRecord> getPrimaryKey() {
		return Keys.CONSTRAINT_4;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<UsersRecord>> getKeys() {
		return Arrays.<UniqueKey<UsersRecord>>asList(Keys.CONSTRAINT_4);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Users as(String alias) {
		return new Users(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Users rename(String name) {
		return new Users(name, null);
	}
}