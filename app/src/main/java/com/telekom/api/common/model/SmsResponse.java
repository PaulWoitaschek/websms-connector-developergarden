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

package com.telekom.api.common.model;

public class SmsResponse extends TelekomResponse {

	private SmsResponseStatus requestError;
	private SmsResponseStatus deliveryInfoList;
	private SmsResponseStatus deliveryReceiptSubscription;
	private SmsResponseStatus inboundSMSMessageList;
	private SmsResponseStatus receiveNotificationResponse;
	private SmsResponseStatus subscription;
	private SmsResponseStatus outboundSMSMessageRequest;
	
	/**
	 * @return standard SMS Request response on success
	 */
	public SmsResponseStatus getOutboundSMSMessageRequest() {
		return this.outboundSMSMessageRequest;
	}
	
	public void setOutboundSMSMessageRequest(SmsResponseStatus outboundSMSMessageRequest) {
		this.outboundSMSMessageRequest = outboundSMSMessageRequest;
	}
	
	/**
	 * @return Returned error status of an operation
	 */
	public SmsResponseStatus getRequestError() {
		return this.requestError;
	}
	
	public void setRequestError(SmsResponseStatus requestError) {
		this.requestError = requestError;
	}

	/**
	 * @return Returned successful status of an query delivery report operation
	 */
	public SmsResponseStatus getDeliveryInfoList() {
		return deliveryInfoList;
	}

	public void setDeliveryInfoList(SmsResponseStatus deliveryInfoList) {
		this.deliveryInfoList = deliveryInfoList;
	}
	
	/**
	 * @return Returned successful status of an receive sms operation
	 */
	public SmsResponseStatus getInboundSMSMessageList() {
		return inboundSMSMessageList;
	}
	
	public void setInboundSMSMessageList(SmsResponseStatus inboundSMSMessageList) {
		this.inboundSMSMessageList = inboundSMSMessageList;
	}
	
	/**
	 * @return true, if this status represents a successful response
	 */
	public boolean getSuccess() {
		return (requestError == null);
	}

	/**
	 * @return Returned successful status of an notification subscription operation
	 */
	public SmsResponseStatus getDeliveryReceiptSubscription() {
		return deliveryReceiptSubscription;
	}

	public void setDeliveryReceiptSubscription(
			SmsResponseStatus deliveryReceiptSubscription) {
		this.deliveryReceiptSubscription = deliveryReceiptSubscription;
	}

	public SmsResponseStatus getReceiveNotificationResponse() {
		return receiveNotificationResponse;
	}
	
	public void setReceiveNotificationResponse(SmsResponseStatus receiveNotificationResponse) {
		this.receiveNotificationResponse = receiveNotificationResponse;
	}
	
	public SmsResponseStatus getSubscription() {
		return subscription;
	}
	
	
}
