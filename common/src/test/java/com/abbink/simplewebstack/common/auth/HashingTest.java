package com.abbink.simplewebstack.common.auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.ByteBuffer;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class HashingTest {
	
	@Test
	public void getSalt() {
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		ByteSource salt = rng.nextBytes();
		String hashedSalt = salt.toBase64();
		assertThat(hashedSalt.length(), equalTo(24));
	}
	
	@Test
	public void getHash() {
		String input = "Some arbitrary string that could also be a password.";
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		ByteSource salt = rng.nextBytes();
		
		SimpleHash hashed = new Sha512Hash(input, salt, 1024);
		String hashedInput = hashed.toBase64();
		assertThat(hashedInput.length(), equalTo(88));
	}
	
	@Test
	public void getString() {
		char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		
		int wordLength = 20;
		byte[] numbers = rng.nextBytes(4*wordLength).getBytes();
		
		char[] word = new char[wordLength];
		
		ByteBuffer bb = ByteBuffer.wrap(numbers);
		for (int i=0; i<wordLength; i++) {
			int pos = Math.abs(bb.getInt()) % alphabet.length;
			word[i] = alphabet[pos];
		}
		
		assertThat(new String(word), equalTo("aa"));
	}
}
