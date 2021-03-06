/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated.tables.records;


import com.abbink.simplewebstack.common.data.generated.tables.ExternalIds;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ExternalIdsRecord extends UpdatableRecordImpl<ExternalIdsRecord> implements Record2<String, String> {

	private static final long serialVersionUID = 1448005282;

	/**
	 * Setter for <code>PUBLIC.EXTERNAL_IDS.XID</code>.
	 */
	public void setXid(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.EXTERNAL_IDS.XID</code>.
	 */
	public String getXid() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.EXTERNAL_IDS.TYPE</code>.
	 */
	public void setType(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.EXTERNAL_IDS.TYPE</code>.
	 */
	public String getType() {
		return (String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<String> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<String, String> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<String, String> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return ExternalIds.EXTERNAL_IDS.XID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return ExternalIds.EXTERNAL_IDS.TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getXid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExternalIdsRecord value1(String value) {
		setXid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExternalIdsRecord value2(String value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExternalIdsRecord values(String value1, String value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ExternalIdsRecord
	 */
	public ExternalIdsRecord() {
		super(ExternalIds.EXTERNAL_IDS);
	}

	/**
	 * Create a detached, initialised ExternalIdsRecord
	 */
	public ExternalIdsRecord(String xid, String type) {
		super(ExternalIds.EXTERNAL_IDS);

		setValue(0, xid);
		setValue(1, type);
	}
}
