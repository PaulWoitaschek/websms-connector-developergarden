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
import com.telekom.api.sendsms.OutboundEncoding;
import com.telekom.api.sendsms.OutboundSMSType;

/**
 * Parameter object for SendSms call
 */
public class SendSmsRequest extends TelekomRequest {

	private String[] address = null;

	private String message = null;

	private String senderAddress = null;
	
	private String senderName = null;

	private OutboundSMSType type = null;

	private String account = null;
	
	private String callbackData = null;
	
	private String notifyURL = null;
	
	private OutboundEncoding encoding = null;
	
	private String clientCorrelator = null;

	
	@JsonProperty(value = "address")
	@Required
	public String[] getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            One (or multiple comma separated) recipient phone number(s). Each starting with tel:+49...
	 */
	public void setAddress(String[] address) {
		this.address = address;
	}

	@Required
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            The message to send
	 */
	public void setMessage(String message) {
		this.message = message;
	}

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
	
	@JsonProperty(value = "senderName")
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName
	 *            Sendername, as shown at the client
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	@Required
	public OutboundSMSType getType() {
		return type;
	}

	/**
	 * @param type
	 *            Type of SMS to be send
	 */
	public void setType(OutboundSMSType type) {
		this.type = type;
	}

	@JsonProperty(value = "account")
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            Account-ID of the sub account which should be billed for this
	 *            service call
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	@JsonProperty(value = "receiptRequest.callbackData")	
	public String getCallbackData() {
		return callbackData;
	}

	/**
	 * @param callbackData
	 *            message identifier for notification
	 */
	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}

	@JsonProperty(value = "receiptRequest.notifyURL")
	public String getNotifyURL() {
		return notifyURL;
	}

	/**
	 * @param notifyURL
	 *            url to send the notification to
	 */
	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public OutboundEncoding getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding
	 *            specifies request sms character encoding
	 */
	public void setEncoding(OutboundEncoding encoding) {
		this.encoding = encoding;
	}
	
	@JsonProperty(value = "clientCorrelator")
	public String getClientCorrelator() {
		return clientCorrelator;
	}

	/**
	 * @param clientCorrelator
	 *            identifies requests
	 */
	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}

}
