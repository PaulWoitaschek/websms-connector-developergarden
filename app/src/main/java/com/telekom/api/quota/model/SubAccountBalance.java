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

package com.telekom.api.quota.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Detail of a sub account
 */
public class SubAccountBalance {

	private String account;

	private Integer credits;

	public SubAccountBalance(@JsonProperty(value = "account") String account,
			@JsonProperty(value = "credits") Integer credits) {
		this.account = account;
		this.credits = credits;
	}

	/**
	 * @return Sub account ID
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @return Balance of this sub account
	 */
	public Integer getCredits() {
		return credits;
	}

}
