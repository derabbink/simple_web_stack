package com.abbink.simplewebstack.common.auth;

import java.nio.ByteBuffer;

import javax.inject.Inject;

import org.apache.shiro.crypto.RandomNumberGenerator;

public class RandomStringGenerator {
	public static final String DEFAULT_ALPHABET = "ABCDEFGHIHJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	@Inject
	RandomNumberGenerator rng;
	
	private char[] alphabet;
	
	public RandomStringGenerator() {
		this.alphabet = DEFAULT_ALPHABET.toCharArray();
	}
	
	public String getRandomString(int length) {
		byte[] numbers = rng.nextBytes(4*length).getBytes();
		
		char[] word = new char[length];
		ByteBuffer bb = ByteBuffer.wrap(numbers);
		for (int i=0; i<length; i++) {
			int pos = Math.abs(bb.getInt()) % alphabet.length;
			word[i] = alphabet[pos];
		}
		
		return new String(word);
	}
}
