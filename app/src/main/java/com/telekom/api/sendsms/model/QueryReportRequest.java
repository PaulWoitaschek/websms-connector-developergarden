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

public class QueryReportRequest extends TelekomRequest {
	
	private String senderAddress;
	
	@JsonProperty(value = "senderAddress")
	@Required
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * @param senderAddress
	 *            Sender, as shown at the client
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
}