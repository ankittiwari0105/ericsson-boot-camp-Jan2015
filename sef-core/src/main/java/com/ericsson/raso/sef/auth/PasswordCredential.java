package com.ericsson.raso.sef.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

import com.ericsson.raso.sef.core.FrameworkException;
import com.ericsson.raso.sef.core.SecureSerializationHelper;

public class PasswordCredential implements Credential {

	private String		simplePassword	= null;
	private byte[]		cipheredPassword = null;
	private Algorithm	algorithm		= Algorithm.UNDEF;
	
	public PasswordCredential() {
	}

	public PasswordCredential(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public String getClearPassword() {
		return this.simplePassword;
	}

	public byte[] getCipheredPassword() {
		return this.cipheredPassword;
	}

	public void setPassword(String clearPassword) throws FrameworkException {
		this.simplePassword = clearPassword;
		this.cipheredPassword = this.encryptPassword(clearPassword);
	}

	@Override
	public Type getCredentialType() {
		return Type.PASSWORD;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	private byte[] encryptPassword(String password) throws FrameworkException {
		switch (this.algorithm) {
			case BASIC:
				return encodeBase64(password);
			case DES:
				return encryptDes(password);
			case DIGEST_MD5:
				return createMd5Hash(password);
			case DIGEST_SHA1:
				return createSha1Hash(password);
			case UNDEF:
			default:
				throw new FrameworkException("Algorithm not set yet!!");
		}
	}

	private byte[] createMd5Hash(String password) throws FrameworkException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new FrameworkException("Failed to MD5 Digest (" + password + ")", e);
		}
	}

	private byte[] createSha1Hash(String password) throws FrameworkException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			return md.digest(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new FrameworkException("Failed to SHA-1 Digest (" + password + ")", e);
		}
	}

	private byte[] encodeBase64(String password) {
		return DatatypeConverter.printBase64Binary(password.getBytes()).getBytes();
	}

	private byte[] encryptDes(String password) throws FrameworkException {
		SecureSerializationHelper ssh = new SecureSerializationHelper();
		return ssh.encrypt(password);
	}

}
