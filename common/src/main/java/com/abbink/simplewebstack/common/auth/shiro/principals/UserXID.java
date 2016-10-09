package com.abbink.simplewebstack.common.auth.shiro.principals;

/**
 * XID stands for External ID
 */
public abstract class UserXID extends Principal<String> {
	private static final long serialVersionUID = 8678510999684571478L;
	
	public UserXID(String xID) {
		super(xID);
	}
}
