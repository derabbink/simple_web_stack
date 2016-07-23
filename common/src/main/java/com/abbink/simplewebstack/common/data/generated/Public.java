/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated;


import com.abbink.simplewebstack.common.data.generated.Sequences;
import com.abbink.simplewebstack.common.data.generated.tables.AccessTokens;
import com.abbink.simplewebstack.common.data.generated.tables.AppScopedIds;
import com.abbink.simplewebstack.common.data.generated.tables.Apps;
import com.abbink.simplewebstack.common.data.generated.tables.ExternalIds;
import com.abbink.simplewebstack.common.data.generated.tables.RememberMeTokens;
import com.abbink.simplewebstack.common.data.generated.tables.SchemaVersion;
import com.abbink.simplewebstack.common.data.generated.tables.Sessions;
import com.abbink.simplewebstack.common.data.generated.tables.Something;
import com.abbink.simplewebstack.common.data.generated.tables.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Public extends SchemaImpl {

	private static final long serialVersionUID = 1455344214;

	/**
	 * The reference instance of <code>PUBLIC</code>
	 */
	public static final Public PUBLIC = new Public();

	/**
	 * No further instances allowed
	 */
	private Public() {
		super("PUBLIC");
	}

	@Override
	public final List<Sequence<?>> getSequences() {
		List result = new ArrayList();
		result.addAll(getSequences0());
		return result;
	}

	private final List<Sequence<?>> getSequences0() {
		return Arrays.<Sequence<?>>asList(
			Sequences.SYSTEM_SEQUENCE_09AC5C05_ADBC_4A7C_83B8_51DAD55E23D9,
			Sequences.SYSTEM_SEQUENCE_17E0C4E6_64CC_4E59_B14A_23E15EE11076,
			Sequences.SYSTEM_SEQUENCE_4EB9AA68_C4C9_47F7_9ACA_6CBB83136594,
			Sequences.SYSTEM_SEQUENCE_6862828B_9E14_43B5_AA22_FE7A83CE2895);
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			SchemaVersion.SCHEMA_VERSION,
			ExternalIds.EXTERNAL_IDS,
			Users.USERS,
			Apps.APPS,
			AppScopedIds.APP_SCOPED_IDS,
			AccessTokens.ACCESS_TOKENS,
			Sessions.SESSIONS,
			RememberMeTokens.REMEMBER_ME_TOKENS,
			Something.SOMETHING);
	}
}
