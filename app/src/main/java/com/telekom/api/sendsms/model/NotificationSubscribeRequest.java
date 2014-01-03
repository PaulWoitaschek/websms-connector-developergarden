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

import com.telekom.api.common.model.TelekomRequest;
import com.telekom.api.common.model.validation.Required;

public class NotificationSubscribeRequest extends TelekomRequest {

	private String senderAddress;
	
	private String callbackData;
	
	private String clientCorrelator;
	
	private String notifyURL;
	
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
	
	public String getCallbackData() {
		return callbackData;
	}

	/**
	 * @param callbackData
	 *            data that you like to be included in the notification that is send to your client
	 */
	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}
	
	public String getClientCorrelator() {
		return clientCorrelator;
	}
	
	/**
	 * @param clientCorrelator
	 *            uniquely identifies this subscription request. When the request fails during a
	 *            communication error, using the same clientCorrelator when retrying allows to avoid
	 *            creating a duplicate subscription
	 */
	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}
	
	@Required
	public String getNotifyURL() {
		return notifyURL;
	}

	/**
	 * @param notifyURL
	 *            URI which will be used by the server to POST the notifications to you. should be set
	 *            to the URL of your own listener application. sent within callbackReference object.
	 */
	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}
}
