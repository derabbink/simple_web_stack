/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated.tables.daos;


import com.abbink.simplewebstack.common.data.generated.tables.ExternalIds;
import com.abbink.simplewebstack.common.data.generated.tables.records.ExternalIdsRecord;

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
public class ExternalIdsDao extends DAOImpl<ExternalIdsRecord, com.abbink.simplewebstack.common.data.generated.tables.pojos.ExternalIds, String> {

	/**
	 * Create a new ExternalIdsDao without any configuration
	 */
	public ExternalIdsDao() {
		super(ExternalIds.EXTERNAL_IDS, com.abbink.simplewebstack.common.data.generated.tables.pojos.ExternalIds.class);
	}

	/**
	 * Create a new ExternalIdsDao with an attached configuration
	 */
	public ExternalIdsDao(Configuration configuration) {
		super(ExternalIds.EXTERNAL_IDS, com.abbink.simplewebstack.common.data.generated.tables.pojos.ExternalIds.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getId(com.abbink.simplewebstack.common.data.generated.tables.pojos.ExternalIds object) {
		return object.getXid();
	}

	/**
	 * Fetch records that have <code>XID IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.ExternalIds> fetchByXid(String... values) {
		return fetch(ExternalIds.EXTERNAL_IDS.XID, values);
	}

	/**
	 * Fetch a unique record that has <code>XID = value</code>
	 */
	public com.abbink.simplewebstack.common.data.generated.tables.pojos.ExternalIds fetchOneByXid(String value) {
		return fetchOne(ExternalIds.EXTERNAL_IDS.XID, value);
	}

	/**
	 * Fetch records that have <code>TYPE IN (values)</code>
	 */
	public List<com.abbink.simplewebstack.common.data.generated.tables.pojos.ExternalIds> fetchByType(String... values) {
		return fetch(ExternalIds.EXTERNAL_IDS.TYPE, values);
	}
}
