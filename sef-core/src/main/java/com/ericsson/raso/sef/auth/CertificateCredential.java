package com.ericsson.raso.sef.auth;

public class CertificateCredential implements Credential {

	public CertificateCredential() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Type getCredentialType() {
		return Type.CERTIFICATE;
	}

}
