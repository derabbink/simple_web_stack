/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.common.data.generated;


import com.abbink.simplewebstack.common.data.generated.Public;

import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in PUBLIC
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

	/**
	 * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_A674379B_6B84_4F5B_B7AD_3B27FCE70480</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_A674379B_6B84_4F5B_B7AD_3B27FCE70480 = new SequenceImpl<Long>("SYSTEM_SEQUENCE_A674379B_6B84_4F5B_B7AD_3B27FCE70480", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);

	/**
	 * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_B5BB486F_C46A_41DB_ADF2_6ADDEF534060</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_B5BB486F_C46A_41DB_ADF2_6ADDEF534060 = new SequenceImpl<Long>("SYSTEM_SEQUENCE_B5BB486F_C46A_41DB_ADF2_6ADDEF534060", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);

	/**
	 * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_CE3F30E2_D169_43B6_A70F_6DD4D9840FFF</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_CE3F30E2_D169_43B6_A70F_6DD4D9840FFF = new SequenceImpl<Long>("SYSTEM_SEQUENCE_CE3F30E2_D169_43B6_A70F_6DD4D9840FFF", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);

	/**
	 * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_FCFF2B2F_ABE8_4BBE_A021_E8D3401ED37A</code>
	 */
	public static final Sequence<Long> SYSTEM_SEQUENCE_FCFF2B2F_ABE8_4BBE_A021_E8D3401ED37A = new SequenceImpl<Long>("SYSTEM_SEQUENCE_FCFF2B2F_ABE8_4BBE_A021_E8D3401ED37A", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);
}
