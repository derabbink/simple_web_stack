package com.abbink.simplewebstack.common.auth.shiro.principals;

import java.io.Serializable;

public class Principal<T> implements Serializable {
	private static final long serialVersionUID = -2066388202544708457L;
	
	private T value;
	
	public Principal(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
}
