/*
 * This file is part of the Telekom Java SDK
 * Copyright 2010 Deutsche Telekom AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.telekom.api.common.auth;

import java.io.IOException;
import java.util.Calendar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telekom.api.common.Base64;
import com.telekom.api.common.HttpMethod;
import com.telekom.api.common.TelekomException;
import com.telekom.api.common.webrequest.TelekomWebRequest;
import com.telekom.api.common.webrequest.WebResponse;

/**
 * Authentication to Telekom services with OAuth2 (redirection)
 */
public class TelekomOAuth2Auth extends TelekomAuth {

	/**
	 * URL to OAuth server. Can be overwritten.
	 */
	public static String BaseUrl = "https://global.telekom.com/gcp-web-api";

	private String clientId;

	private String clientSecret;
	
	private String scope;

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}
	
	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Create a new OAuth2 authentication object without tokens
	 * 
	 * @param clientId
	 *            Your application's OAuth client ID
	 * @param clientSecret
	 *            Your application's OAuth secret (null if none)
	 * @param scope
	 *            Your application's scope
	 */
	public TelekomOAuth2Auth(String clientId, String clientSecret, String scope) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
	}

	/**
	 * Request the token to access Telekom services via client_credentials (clientId, clientSecret)
	 * 
	 * @throws java.io.IOException
	 */
	public void requestAccessToken() {
		String uri = BaseUrl + "/oauth";

		try {
			TelekomWebRequest request = new TelekomWebRequest(uri,
					HttpMethod.POST);
			request.setAuthHeader("Basic "
					+ Base64.encodeBase64String(clientId + ":" + clientSecret));
			request.setRawContent(("grant_type=client_credentials"
					+ "&scope=" + scope).getBytes(), "application/x-www-form-urlencoded");
			WebResponse response = request.executeRaw();
			if (response != null) {				
				ObjectMapper m = new ObjectMapper();
				JsonNode rootNode = m.readValue(response.getResponseStream(),
						JsonNode.class);
				
				if (rootNode.has("access_token") && rootNode.has("expires_in")) {
					accessToken = rootNode.get("access_token").textValue();
					accessTokenValidUntil = Calendar.getInstance();
					accessTokenValidUntil.setTimeInMillis( Calendar.getInstance().getTimeInMillis() + (rootNode.get("expires_in").asLong() * 1000) );
				} else if (rootNode.has("error") && rootNode.has("error_description")) {
					System.err.println("Exception: " + rootNode.get("error").textValue() + ": " + rootNode.get("error_description").textValue());
					throw new TelekomException(rootNode.get("error").textValue() + ": " + rootNode.get("error_description").textValue());
				} else {
					System.err.println("Unknown error occured.");
					throw new TelekomException("Unknown error occured");
				}
			}
		} catch (IOException e1) {
			// ignore
			System.err.println("Exception: " + e1.getMessage());
		}
	}

	/**
	 * Returns if there is a valid access token. If not, tries to refresh it.
	 */
	@Override
	public boolean hasValidToken() {
		// Is the current access token still valid?
		if (super.hasValidToken()) {
			// it is
			return true;
		} else {
			// it is not
			return false;
		}
	}

}
