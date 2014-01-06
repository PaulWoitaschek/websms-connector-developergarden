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

package com.telekom.api.common.auth.oauth2;

import com.telekom.api.common.model.HttpParameter;
import com.telekom.api.common.model.TelekomRequest;
import com.telekom.api.common.model.validation.Required;

/**
 * Base class for OAuth requests
 */
public class OAuth2Request extends TelekomRequest {

	private String clientId;

	private String clientSecret;
	
	private String scope;

	/**
	 * @return the clientId
	 */
	@HttpParameter(name = "client_id")
	@Required
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the clientSecret
	 */
	@HttpParameter(name = "client_secret")
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret
	 *            the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	/**
	 * @return the scope
	 */
	@HttpParameter(name = "scope")
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.clientSecret = scope;
	}

}
