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

package com.telekom.api.common;

import com.telekom.api.common.auth.TelekomAuth;
import com.telekom.api.common.model.TelekomRequest;
import com.telekom.api.common.webrequest.TelekomJsonWebRequest;

/**
 * Base class for all Telekom REST SDK clients
 */
public abstract class TelekomClient {

	/**
	 * Address of Telekom API server.
	 */
	public static String TelekomBaseUrl = "https://gateway.developer.telekom.com";


	/**
	 * Base URL to the concrete service.
	 */
	private String serviceBaseUrl;

	/**
	 * Authentication object that provides us with an access token for the
	 * requests
	 */
	protected TelekomAuth authentication;

	protected String getServiceBaseUrl() {
		return serviceBaseUrl;
	}

	protected String EnvironmentToString(ServiceEnvironment environment) {
		switch (environment) {
		case SANDBOX:
			return "sandbox";
		case PRODUCTION:
			return "production";
		case MOCK:
			return "mock";
		case PREMIUM:
			return "premium";
		case BUDGET:
			return "budget";
		default:
			throw new RuntimeException("Unexpected environment");
		}
	}

	protected String urlEncode(String part) {
		return UriHelper.escapeUriString(part);
	}

	public TelekomClient(TelekomAuth authentication,
			ServiceEnvironment environment, String baseUrlTemplate) {
		this.authentication = authentication;

		String environmentString = EnvironmentToString(environment);
		
		serviceBaseUrl = TelekomBaseUrl
				+ String.format(baseUrlTemplate, urlEncode(environmentString));
	}

	protected void ensureRequestValid(TelekomRequest request) {
		if (request == null)
			throw new TelekomParameterException(
					"request parameter object is null");

		request.enforceRequiredFields();
	}

	protected void ensureNotNull(String argument) {
		if (argument == null)
			throw new TelekomParameterException("required argument is null");
	}

	/**
	 * Create a web request with access token
	 * 
	 * @param uri
	 *            Target URI of the request
	 * @param method
	 *            HTTP method of the request
	 * @return configured web request with added header fields
	 */
	protected TelekomJsonWebRequest createAuthenticatedRequest(String uri,
			HttpMethod method) {

		return createAuthenticatedRequest(uri, method, null);
	}

	/**
	 * Create a web request with access token and serialized parameters
	 * 
	 * @param uri
	 *            Target URI of the request
	 * @param method
	 *            HTTP method of the request
	 * @param request
	 *            Object containing request parameters
	 * @return configured web request with added header fields
	 */
	protected TelekomJsonWebRequest createAuthenticatedRequest(String uri,
			HttpMethod method, TelekomRequest request) {

		if (!authentication.hasToken())
			throw new TelekomException("No access token fetched.");
		TelekomJsonWebRequest webRequest = new TelekomJsonWebRequest(uri,
				method);
		webRequest
				.setAuthHeader("OAuth realm=\"developergarden.com\", oauth_token=\""
						+ authentication.getAccessToken() + "\"");
		if (request != null)
			request.buildRequestParameters(webRequest);
		return webRequest;
	}

}
