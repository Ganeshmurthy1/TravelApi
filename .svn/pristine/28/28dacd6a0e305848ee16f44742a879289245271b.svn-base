package com.tayyarah.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;


public class encryptions {
	
	private final String PRIVATEKEY="Lintas010";
	static final Logger logger = Logger.getLogger(encryptions.class);
	Cipher ecipher;
	Cipher dcipher;
	public encryptions(String pass) {

		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
				(byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };

		int iterationCount = 10;
		try {

			KeySpec keySpec = new PBEKeySpec(pass.toCharArray(), salt,
					iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
					iterationCount);
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (InvalidAlgorithmParameterException e) {
			logger.error("EXCEPTION: InvalidAlgorithmParameterException");
		} catch (InvalidKeySpecException e) {
			logger.error("EXCEPTION: InvalidKeySpecException");
		} catch (NoSuchPaddingException e) {
			logger.error("EXCEPTION: NoSuchPaddingException");
		} catch (NoSuchAlgorithmException e) {
			logger.error("EXCEPTION: NoSuchAlgorithmException");
		} catch (InvalidKeyException e) {
			logger.error("EXCEPTION: InvalidKeyException");
		}
	}

	public encryptions() {
		super();
		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
				(byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };
		int iterationCount = 10;
		try {
			KeySpec keySpec = new PBEKeySpec(PRIVATEKEY.toCharArray(), salt,
					iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
					iterationCount);
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (InvalidAlgorithmParameterException e) {
			logger.error("EXCEPTION: InvalidAlgorithmParameterException");
		} catch (InvalidKeySpecException e) {
			logger.error("EXCEPTION: InvalidKeySpecException");
		} catch (NoSuchPaddingException e) {
			logger.error("EXCEPTION: NoSuchPaddingException");
		} catch (NoSuchAlgorithmException e) {
			logger.error("EXCEPTION: NoSuchAlgorithmException");
		} catch (InvalidKeyException e) {
			logger.error("EXCEPTION: InvalidKeyException");
		}
	}

	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf16 = str.getBytes("UTF32");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf16); 
			return new sun.misc.BASE64Encoder().encode(enc);

		} catch (BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
		return null;
	}

	public String decrypt(String str) {

		try {
			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

			// Decrypt
			byte[] utf16 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf16, "UTF32");

		} catch (BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
		return null;
	}

	public static String encrypt(String plainText, String encryptionKey,String IV) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
		byte[] encryptedByte = cipher.doFinal(plainText.getBytes("UTF-8"));
		String encryptedText = Base64.encodeBase64String(encryptedByte);
		return encryptedText;
	}

	public  String decrypt(String cipherText, String encryptionKey,String IV) throws Exception{
		byte[] encryptedTextByte = Base64.decodeBase64(cipherText);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
		return new String(cipher.doFinal(encryptedTextByte),"UTF-8");
	}
}
