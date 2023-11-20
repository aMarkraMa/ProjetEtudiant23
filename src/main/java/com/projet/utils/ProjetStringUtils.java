package com.projet.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class ProjetStringUtils {
	public static String sha256(String motDePasseOriginal) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] digest = messageDigest.digest(motDePasseOriginal.getBytes(StandardCharsets.UTF_8));
		BigInteger number = new BigInteger(1, digest);
		String motDePasse = number.toString(16);
		while (motDePasse.length() < 64) {
			motDePasse = "0" + motDePasse;
		}
		return motDePasse;
	}
	
	public static boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.@#$%^&+=-])(?=\\S+$).{8}$";
		return Pattern.compile(regex).matcher(password).matches();
	}
	
	public static boolean isValidEmail(String email) {
		String regex = "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$";
		return Pattern.compile(regex).matcher(email).matches();
	}
}
