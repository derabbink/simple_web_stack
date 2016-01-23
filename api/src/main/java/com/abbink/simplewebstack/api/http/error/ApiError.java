package com.abbink.simplewebstack.api.http.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiError {
	private int code;
	private String message;
	
	public ApiError(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	@JsonProperty
	public int getCode() {
		return code;
	}
	
	@JsonProperty
	public String getMessage() {
		return message;
	}
}
