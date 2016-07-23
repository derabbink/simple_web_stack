package com.abbink.simplewebstack.common.auth.shiro.principals;

public class ExternalID extends PrincipalWrapper {
	private static final long serialVersionUID = 8678510999684571478L;
	
	public ExternalID(String xID) {
		super(xID);
	}
}
