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


/**
 * Common status result of SMS operations
 *
 */
public class SmsResponseStatus {

	private SmsResponseStatusValues policyException;
	private SmsResponseStatusValues serviceException;
	private SmsResponseDeliveryValues[] deliveryInfo;
	private SmsResponseSubscriptionValues subscription;
	private SmsResponseInboundSmsMessageValues[] inboundSMSMessage;
	private String criteria;
	private String destinationAddress;
	private String notificationFormat;
	private String clientCorrelator;
	private String account;
	private String resourceURL;
	private String numberOfMessagesInThisBatch;
	private String totalNumberOfPendingMessages;
	private SmsResponseSubscriptionValues callbackReference;
	private String senderAddress;
	private SmsResponseStatus deliveryInfoList;
	private String outboundEncoding;
	private String[] address;
	private SmsResponseMessage outboundSMSTextMessage;
	private SmsResponseMessage outboundSMSBinaryMessage;
	private SmsResponseMessage outboundSMSFlashMessage;
	
	
	
	/**
	 * @return A policyException occured while sending an SMS
	 */
	public SmsResponseStatusValues getPolicyException() {
		return policyException;
	}
	public void setPolicyException(SmsResponseStatusValues policyException) {
		this.policyException = policyException;
	}
	
	/**
	 * @return A serviceException occured while sending an SMS
	 */
	public SmsResponseStatusValues getServiceException() {
		return serviceException;
	}
	public void setServiceException(SmsResponseStatusValues serviceException) {
		this.serviceException = serviceException;
	}
	
	/**
	 * @return A list of deliveryInfos applied when querying sms status
	 */
	public SmsResponseDeliveryValues[] getDeliveryInfo() {
		return deliveryInfo;
	}
	public void setDeliveryInfo(SmsResponseDeliveryValues[] deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	
	/**
	 * @return The resourceURL from borrowed from the request
	 */
	public String getResourceURL() {
		return resourceURL;
	}
	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}
	
	/**
	 * @return The numberOfMessagesInThisBatch borrowed from the request
	 */
	public String getNumberOfMessagesInThisBatch() {
		return numberOfMessagesInThisBatch;
	}
	public void setNumberOfMessagesInThisBatch(String numberOfMessagesInThisBatch) {
		this.numberOfMessagesInThisBatch = numberOfMessagesInThisBatch;
	}
	
	/**
	 * @return The totalNumberOfPendingMessages borrowed from the request
	 */
	public String getTotalNumberOfPendingMessages() {
		return totalNumberOfPendingMessages;
	}
	public void setTotalNumberOfPendingMessages(String totalNumberOfPendingMessages) {
		this.totalNumberOfPendingMessages = totalNumberOfPendingMessages;
	}
	
	/**
	 * @return Subscription data provided by the backend
	 */
	public SmsResponseSubscriptionValues getSubscription() {
		return subscription;
	}
	
	public void setSubscription(SmsResponseSubscriptionValues subscription) {
		this.subscription = subscription;
	}
	
	/**
	 * @return callbackReference data provided by the backend
	 */
	public SmsResponseSubscriptionValues getCallbackReference() {
		return callbackReference;
	}
	
	public void setCallbackReference(SmsResponseSubscriptionValues callbackReference) {
		this.callbackReference = callbackReference;
	}
	
	/**
	 * @return The inboundSMSMessage borrowed from the request
	 */
	public SmsResponseInboundSmsMessageValues[] getInboundSMSMessage() {
		return inboundSMSMessage;
	}
	
	public void setInboundSMSMessage(SmsResponseInboundSmsMessageValues[] inboundSMSMessage) {
		this.inboundSMSMessage = inboundSMSMessage;
	}
	
	/**
	 * @return The criteria borrowed from the request
	 */
	public String getCriteria() {
		return criteria;
	}
	
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	
	/**
	 * @return The destinationAddress borrowed from the request
	 */
	public String getDestinationAddress() {
		return destinationAddress;
	}
	
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	
	/**
	 * @return The notificationFormat borrowed from the request
	 */
	public String getNotificationFormat() {
		return notificationFormat;
	}
	
	public void setNotificationFormat(String notificationFormat) {
		this.notificationFormat = notificationFormat;
	}
	
	/**
	 * @return The clientCorrelator borrowed from the request
	 */
	public String getClientCorrelator() {
		return clientCorrelator;
	}
	
	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}
	
	/**
	 * @return The account borrowed from the request
	 */
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public SmsResponseStatus getDeliveryInfoList() {
		return deliveryInfoList;
	}
	public void setDeliveryInfoList(SmsResponseStatus deliveryInfoList) {
		this.deliveryInfoList = deliveryInfoList;
	}
	public String getOutboundEncoding() {
		return outboundEncoding;
	}
	public void setOutboundEncoding(String outboundEncoding) {
		this.outboundEncoding = outboundEncoding;
	}
	public String[] getAddress() {
		return address;
	}
	public void setAddress(String[] address) {
		this.address = address;
	}
	public SmsResponseMessage getOutboundSMSTextMessage() {
		return outboundSMSTextMessage;
	}
	public void setOutboundSMSTextMessage(SmsResponseMessage outboundSMSTextMessage) {
		this.outboundSMSTextMessage = outboundSMSTextMessage;
	}
	public SmsResponseMessage getOutboundSMSBinaryMessage() {
		return outboundSMSBinaryMessage;
	}
	public void setOutboundSMSBinaryMessage(SmsResponseMessage outboundSMSBinaryMessage) {
		this.outboundSMSBinaryMessage = outboundSMSBinaryMessage;
	}
	public SmsResponseMessage getOutboundSMSFlashMessage() {
		return outboundSMSFlashMessage;
	}
	public void setOutboundSMSFlashMessage(SmsResponseMessage outboundSMSFlashMessage) {
		this.outboundSMSFlashMessage = outboundSMSFlashMessage;
	}
		
}
