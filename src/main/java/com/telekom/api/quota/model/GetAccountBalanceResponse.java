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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telekom.api.common.model.TelekomResponse;

/**
 * Response that contains a list of sub accounts with balance information
 */
public class GetAccountBalanceResponse extends TelekomResponse {

	private List<SubAccountBalance> accounts;

	public GetAccountBalanceResponse(
			@JsonProperty(value = "accounts") List<SubAccountBalance> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return List of sub accounts
	 */
	public List<SubAccountBalance> getAccounts() {
		return accounts;
	}

}
