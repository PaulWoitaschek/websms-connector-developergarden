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

package com.telekom.api.sendsms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telekom.api.common.model.TelekomRequest;
import com.telekom.api.common.model.validation.Required;

/**
 * Parameter object for SendSms call
 */
public class SendSmsRetrieveRequest extends TelekomRequest {

	private String registrationId = null;

	private String account = null;
	
	private int maxBatchSize = 100;

	
	@JsonProperty(value = "registrationId")
	@Required
	public String getRegistrationId() {
		return this.registrationId;
	}

	/**
	 * @param registrationId
	 *            The registrationId to receive the sms from
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getAccount() {
		return this.account;
	}

	/**
	 * @param account
	 *            account, to charge for this action
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	public int getMaxBatchSize() {
		return this.maxBatchSize;
	}
	
	/**
	 * @param maxBatchSize
	 *            maxBatchSize, number of messages to retrieve in this request
	 */
	public void setMaxBatchSize(int maxBatchSize) {
		this.maxBatchSize = maxBatchSize;
	}

}
