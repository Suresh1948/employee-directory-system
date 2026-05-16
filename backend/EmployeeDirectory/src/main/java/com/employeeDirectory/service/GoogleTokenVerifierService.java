
package com.employeeDirectory.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Service
public class GoogleTokenVerifierService {

	@Value("${google.client.id}")
	private String clientId;

	public GoogleIdToken.Payload verify(String idTokenString) throws Exception {

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				GsonFactory.getDefaultInstance()).setAudience(Collections.singletonList(clientId)).build();

		GoogleIdToken idToken = verifier.verify(idTokenString);
		System.out.println("CLIENT_ID = " + clientId);
		System.out.println("TOKEN AUD = " + idToken.getPayload().getAudience());

		return idToken.getPayload();
	}
}
