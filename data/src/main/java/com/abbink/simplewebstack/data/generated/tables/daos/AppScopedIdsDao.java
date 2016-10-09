/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.data.generated.tables.daos;


import com.abbink.simplewebstack.data.generated.tables.AppScopedIds;
import com.abbink.simplewebstack.data.generated.tables.records.AppScopedIdsRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AppScopedIdsDao extends DAOImpl<AppScopedIdsRecord, com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds, Record2<Integer, Integer>> {

    /**
     * Create a new AppScopedIdsDao without any configuration
     */
    public AppScopedIdsDao() {
        super(AppScopedIds.APP_SCOPED_IDS, com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds.class);
    }

    /**
     * Create a new AppScopedIdsDao with an attached configuration
     */
    public AppScopedIdsDao(Configuration configuration) {
        super(AppScopedIds.APP_SCOPED_IDS, com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Record2<Integer, Integer> getId(com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds object) {
        return compositeKeyRecord(object.getUserId(), object.getAppId());
    }

    /**
     * Fetch records that have <code>USER_ID IN (values)</code>
     */
    public List<com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds> fetchByUserId(Integer... values) {
        return fetch(AppScopedIds.APP_SCOPED_IDS.USER_ID, values);
    }

    /**
     * Fetch records that have <code>APP_SCOPED_USER_XID IN (values)</code>
     */
    public List<com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds> fetchByAppScopedUserXid(String... values) {
        return fetch(AppScopedIds.APP_SCOPED_IDS.APP_SCOPED_USER_XID, values);
    }

    /**
     * Fetch records that have <code>APP_ID IN (values)</code>
     */
    public List<com.abbink.simplewebstack.data.generated.tables.pojos.AppScopedIds> fetchByAppId(Integer... values) {
        return fetch(AppScopedIds.APP_SCOPED_IDS.APP_ID, values);
    }
}
