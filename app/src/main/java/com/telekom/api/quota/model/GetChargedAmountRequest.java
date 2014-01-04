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

import com.telekom.api.common.model.HttpParameter;
import com.telekom.api.common.model.NoHttpParameter;
import com.telekom.api.common.model.TelekomRequest;
import com.telekom.api.common.model.validation.Required;

/**
 * Parameters to fetch charged amount of a voice or conference call
 */
public class GetChargedAmountRequest extends TelekomRequest {

	private String id;

	private String accountId;

	@NoHttpParameter
	@Required
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            Voice Call oder Conference Call Id for which costs the request
	 *            is for.
	 */
	public void setId(String id) {
		this.id = id;
	}

	@HttpParameter(name = "accountID")
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            Sub-account to query (if omitted, main account is used)
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
