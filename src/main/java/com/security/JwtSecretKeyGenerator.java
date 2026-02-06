package com.security;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretKeyGenerator {
	public static void main(String[] args) {
		byte[] keybytes = new byte[64];
		
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(keybytes);
		
		String base64Key = Base64.getEncoder().encodeToString(keybytes);
		System.out.println("generated key is: " + base64Key);
	}
}
