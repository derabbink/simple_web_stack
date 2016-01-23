/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated;


import com.abbink.simplewebstack.common.data.generated.Sequences;
import com.abbink.simplewebstack.common.data.generated.tables.AccessTokens;
import com.abbink.simplewebstack.common.data.generated.tables.AppScopedIds;
import com.abbink.simplewebstack.common.data.generated.tables.Apps;
import com.abbink.simplewebstack.common.data.generated.tables.ExternalIds;
import com.abbink.simplewebstack.common.data.generated.tables.SchemaVersion;
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

	private static final long serialVersionUID = 995835329;

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
			Sequences.SYSTEM_SEQUENCE_A674379B_6B84_4F5B_B7AD_3B27FCE70480,
			Sequences.SYSTEM_SEQUENCE_B5BB486F_C46A_41DB_ADF2_6ADDEF534060,
			Sequences.SYSTEM_SEQUENCE_CE3F30E2_D169_43B6_A70F_6DD4D9840FFF,
			Sequences.SYSTEM_SEQUENCE_FCFF2B2F_ABE8_4BBE_A021_E8D3401ED37A);
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
			Something.SOMETHING);
	}
}
