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

package com.telekom.api.sendsms;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.telekom.api.common.HttpMethod;
import com.telekom.api.common.ServiceEnvironment;
import com.telekom.api.common.TelekomClient;
import com.telekom.api.common.auth.TelekomAuth;
import com.telekom.api.common.model.SmsResponse;
import com.telekom.api.common.webrequest.JsonSerializer;
import com.telekom.api.common.webrequest.TelekomJsonWebRequest;
import com.telekom.api.sendsms.model.SendSmsRequest;

/**
 * Wrapper for Telekom Send SMS service
 */
public class SendSmsClient extends TelekomClient {

	/**
	 * URL Path to Send SMS services. Can be overridden if necessary. %s is
	 * replaced by selected environment
	 */
	public static String ServicePath = "/plone/sms/rest/%s/smsmessaging/v1";

	/**
	 * Constructs a Send SMS API client with specified authentication method and
	 * environment.
	 * 
	 * @param authentication
	 *            Authentication instance
	 * @param environment
	 *            Environment used for this client's service invocations
	 */
	public SendSmsClient(TelekomAuth authentication,
			ServiceEnvironment environment) {
		super(authentication, environment, ServicePath);
	}
	
	private TelekomJsonWebRequest createAuthenticatedJsonRequest(String uri,
			SendSmsRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		TelekomJsonWebRequest webRequest = createAuthenticatedRequest(uri,
				HttpMethod.POST);
		
		HashMap<String,HashMap<String, Object>> jsonRequest = new HashMap<String, HashMap<String, Object>>();
		
		HashMap<String,Object> val = new HashMap<String, Object>();
		
		val.put("address", request.getAddress());
		HashMap<String, Object> helper = new HashMap<String, Object>();
		
		String key = "";
		String key2 ="";
		if (request.getType().equals(OutboundSMSType.TEXT)) {
			key = "outboundSMSTextMessage";
			key2 = "message";
		} else if (request.getType().equals(OutboundSMSType.BINARY)) {
			key = "outboundSMSBinaryMessage";
			key2 = "message";
		} else if (request.getType().equals(OutboundSMSType.FLASH)) {
			key = "outboundSMSFlashMessage";
			key2 = "flashMessage";
		}
		helper.put(key2, request.getMessage());
		val.put(key, helper);
		
		val.put("senderAddress", request.getSenderAddress());
		
		if(request.getSenderName() != null) {
			val.put("senderName", request.getSenderName());
		}
		if(request.getAccount() != null) {
			val.put("account", request.getAccount());
		}
		helper = new HashMap<String, Object>();
		if(request.getCallbackData() != null) {
			helper.put("callbackData", request.getCallbackData());
		}
		if(request.getNotifyURL() != null) {
			helper.put("notifyURL", request.getNotifyURL());
		}
		val.put("receiptRequest", helper);
		
		if(request.getEncoding() != null) {
			if (request.getEncoding().equals(OutboundEncoding.GSM)) {
				val.put("outboundEncoding", "7bitGSM");
			} else if (request.getEncoding().equals(OutboundEncoding.UCS)) {
				val.put("outboundEncoding", "16bitUCS2");
			}
		}
		
		if (request.getClientCorrelator() != null) {
			val.put("clientCorrelator", request.getClientCorrelator());
		}
		
		jsonRequest.put("outboundSMSMessageRequest", val);
		
		webRequest.setRawContent(JsonSerializer.serializeToBytes(jsonRequest),
				"application/json; charset=utf-8");
		return webRequest;
	}

	/**
	 * Send a SMS
	 * 
	 * @param request
	 *            Parameter object
	 * @return Result of this operation
	 * @throws java.io.IOException
	 */
	public SmsResponse sendSms(SendSmsRequest request) throws IOException {
		ensureRequestValid(request);

		String uri = getServiceBaseUrl() + "/outbound/%s/requests";
		
		uri = String.format(uri, urlEncode(request.getSenderAddress()));
		
		return createAuthenticatedJsonRequest(uri, request)
				.execute(SmsResponse.class);
	}

}
