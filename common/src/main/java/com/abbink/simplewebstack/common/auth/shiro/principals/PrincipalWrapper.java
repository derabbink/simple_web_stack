package com.abbink.simplewebstack.common.auth.shiro.principals;

import java.io.Serializable;

public class PrincipalWrapper implements Serializable {
	private static final long serialVersionUID = -2066388202544708457L;
	
	private String value;
	
	public PrincipalWrapper(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return getValue();
	}
}
