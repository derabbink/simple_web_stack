package com.abbink.simplewebstack.common.auth.shiro.principals;

public class AppScopedXID extends UserXID {
	private static final long serialVersionUID = -5524119730782715795L;
	
	public AppScopedXID(String xID) {
		super(xID);
	}
}
