/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.data.generated;


import com.abbink.simplewebstack.data.generated.tables.AccessTokens;
import com.abbink.simplewebstack.data.generated.tables.AppRoles;
import com.abbink.simplewebstack.data.generated.tables.AppScopedIds;
import com.abbink.simplewebstack.data.generated.tables.Apps;
import com.abbink.simplewebstack.data.generated.tables.ExternalIds;
import com.abbink.simplewebstack.data.generated.tables.SchemaVersion;
import com.abbink.simplewebstack.data.generated.tables.Something;
import com.abbink.simplewebstack.data.generated.tables.Users;
import com.abbink.simplewebstack.data.generated.tables.records.AccessTokensRecord;
import com.abbink.simplewebstack.data.generated.tables.records.AppRolesRecord;
import com.abbink.simplewebstack.data.generated.tables.records.AppScopedIdsRecord;
import com.abbink.simplewebstack.data.generated.tables.records.AppsRecord;
import com.abbink.simplewebstack.data.generated.tables.records.ExternalIdsRecord;
import com.abbink.simplewebstack.data.generated.tables.records.SchemaVersionRecord;
import com.abbink.simplewebstack.data.generated.tables.records.SomethingRecord;
import com.abbink.simplewebstack.data.generated.tables.records.UsersRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>PUBLIC</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<SomethingRecord, Integer> IDENTITY_SOMETHING = Identities0.IDENTITY_SOMETHING;
    public static final Identity<AppsRecord, Integer> IDENTITY_APPS = Identities0.IDENTITY_APPS;
    public static final Identity<UsersRecord, Integer> IDENTITY_USERS = Identities0.IDENTITY_USERS;
    public static final Identity<AccessTokensRecord, Integer> IDENTITY_ACCESS_TOKENS = Identities0.IDENTITY_ACCESS_TOKENS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<SchemaVersionRecord> SCHEMA_VERSION_PK = UniqueKeys0.SCHEMA_VERSION_PK;
    public static final UniqueKey<SomethingRecord> CONSTRAINT_8 = UniqueKeys0.CONSTRAINT_8;
    public static final UniqueKey<AppsRecord> CONSTRAINT_1 = UniqueKeys0.CONSTRAINT_1;
    public static final UniqueKey<UsersRecord> CONSTRAINT_4 = UniqueKeys0.CONSTRAINT_4;
    public static final UniqueKey<ExternalIdsRecord> CONSTRAINT_37 = UniqueKeys0.CONSTRAINT_37;
    public static final UniqueKey<AppRolesRecord> CONSTRAINT_4A = UniqueKeys0.CONSTRAINT_4A;
    public static final UniqueKey<AppScopedIdsRecord> CONSTRAINT_9 = UniqueKeys0.CONSTRAINT_9;
    public static final UniqueKey<AccessTokensRecord> CONSTRAINT_5 = UniqueKeys0.CONSTRAINT_5;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<SomethingRecord, Integer> IDENTITY_SOMETHING = createIdentity(Something.SOMETHING, Something.SOMETHING.ID);
        public static Identity<AppsRecord, Integer> IDENTITY_APPS = createIdentity(Apps.APPS, Apps.APPS.ID);
        public static Identity<UsersRecord, Integer> IDENTITY_USERS = createIdentity(Users.USERS, Users.USERS.ID);
        public static Identity<AccessTokensRecord, Integer> IDENTITY_ACCESS_TOKENS = createIdentity(AccessTokens.ACCESS_TOKENS, AccessTokens.ACCESS_TOKENS.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<SchemaVersionRecord> SCHEMA_VERSION_PK = createUniqueKey(SchemaVersion.SCHEMA_VERSION, "schema_version_pk", SchemaVersion.SCHEMA_VERSION.INSTALLED_RANK);
        public static final UniqueKey<SomethingRecord> CONSTRAINT_8 = createUniqueKey(Something.SOMETHING, "CONSTRAINT_8", Something.SOMETHING.ID);
        public static final UniqueKey<AppsRecord> CONSTRAINT_1 = createUniqueKey(Apps.APPS, "CONSTRAINT_1", Apps.APPS.ID);
        public static final UniqueKey<UsersRecord> CONSTRAINT_4 = createUniqueKey(Users.USERS, "CONSTRAINT_4", Users.USERS.ID);
        public static final UniqueKey<ExternalIdsRecord> CONSTRAINT_37 = createUniqueKey(ExternalIds.EXTERNAL_IDS, "CONSTRAINT_37", ExternalIds.EXTERNAL_IDS.XID);
        public static final UniqueKey<AppRolesRecord> CONSTRAINT_4A = createUniqueKey(AppRoles.APP_ROLES, "CONSTRAINT_4A", AppRoles.APP_ROLES.USER_ID, AppRoles.APP_ROLES.APP_ID);
        public static final UniqueKey<AppScopedIdsRecord> CONSTRAINT_9 = createUniqueKey(AppScopedIds.APP_SCOPED_IDS, "CONSTRAINT_9", AppScopedIds.APP_SCOPED_IDS.USER_ID, AppScopedIds.APP_SCOPED_IDS.APP_ID);
        public static final UniqueKey<AccessTokensRecord> CONSTRAINT_5 = createUniqueKey(AccessTokens.ACCESS_TOKENS, "CONSTRAINT_5", AccessTokens.ACCESS_TOKENS.ID);
    }
}
