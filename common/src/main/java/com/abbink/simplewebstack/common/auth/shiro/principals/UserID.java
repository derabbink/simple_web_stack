package com.abbink.simplewebstack.common.auth.shiro.principals;


public class UserID extends Principal<Integer> {
	private static final long serialVersionUID = -7134700819597171742L;

	public UserID(int value) {
		super(value);
	}
}
