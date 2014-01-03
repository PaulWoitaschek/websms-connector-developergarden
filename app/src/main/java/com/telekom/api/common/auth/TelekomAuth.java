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

import java.util.Calendar;

/**
 * Base class for all authentication methods. Provides the access token for
 * Telekom services to SDK clients.
 */
public class TelekomAuth {

	protected String accessToken;

	protected Calendar accessTokenValidUntil;

	/**
	 * Start a new authentication
	 */
	public TelekomAuth() {

	}

	/**
	 * Continue an already authenticated session
	 * 
	 * @param accessToken
	 *            Stored token
	 * @param accessTokenValidUntil
	 *            Stored token expiration date
	 */
	public TelekomAuth(String accessToken, Calendar accessTokenValidUntil) {
		this.accessToken = accessToken;
		this.accessTokenValidUntil = accessTokenValidUntil;
	}

	/**
	 * Checks if there is an access token (whether valid or not)
	 * 
	 * @return if token has been fetched
	 */
	public boolean hasToken() {
		return (accessToken != null);
	}

	/**
	 * Returns if we have a valid (not expired) access token. This call tries to
	 * refresh the token, if authentication mode supports it.
	 * 
	 * @return if valid token is present after method call
	 */
	public boolean hasValidToken() {
		return (accessToken != null)
				&& Calendar.getInstance().before(accessTokenValidUntil);
	}

	/**
	 * @return the current access token or null, if not present
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @return expiration date of current access token
	 */
	public Calendar getAccessTokenValidUntil() {
		return accessTokenValidUntil;
	}

}
