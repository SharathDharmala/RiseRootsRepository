package com.riseroots.usermanagement.util;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;
import com.nimbusds.jose.*;
import java.util.UUID;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "a-very-long-secret-key-at-least-32-bytes-in-length"; // Must be 32+ bytes

	public String generateToken(String username) {
		try {
			JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(username).issuer("your-app")
					.expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
					.jwtID(UUID.randomUUID().toString()) // Unique identifier
					.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			signedJWT.sign(signer);
			return signedJWT.serialize(); // Return JWT as a string
		} catch (JOSEException e) {
			throw new RuntimeException("Error generating JWT token", e);
		}
	}

	public boolean validateToken(String token, String username) {
		try {
			// Parse the token
			SignedJWT signedJWT = SignedJWT.parse(token);

			// Verify the signature with the secret key
			MACVerifier verifier = new MACVerifier(SECRET_KEY);
			if (!signedJWT.verify(verifier)) {
				return false;
			}

			// Extract claims and validate
			JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
			String tokenUsername = claimsSet.getSubject();
			Date expirationTime = claimsSet.getExpirationTime();

			return username.equals(tokenUsername) && expirationTime.after(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String extractUsername(String token) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			return signedJWT.getJWTClaimsSet().getSubject();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
