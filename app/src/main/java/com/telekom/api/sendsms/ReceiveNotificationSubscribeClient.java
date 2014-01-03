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
import com.telekom.api.sendsms.model.ReceiveNotificationSubscribeRequest;

public class ReceiveNotificationSubscribeClient extends TelekomClient {

	/**
	 * URL Path to Send SMS Delivery Notification Subscribe services. Can be overridden if necessary. %s is
	 * replaced by selected environment
	 */
	public static String ServicePath = "/plone/sms/rest/%s/smsmessaging/v1";
	
	/**
	 * Constructs a Send SMS API Delivery Notification Subscription client with specified authentication method and
	 * environment.
	 * 
	 * @param authentication
	 *            Authentication instance
	 * @param environment
	 *            Environment used for this client's service invocations
	 */
	public ReceiveNotificationSubscribeClient(TelekomAuth authentication,
			ServiceEnvironment environment) {
		super(authentication, environment, ServicePath);
	}
	
	/**
	 * Subscribe for Delivery Notifications
	 * 
	 * @param request
	 *            Parameter object
	 * @return Result of this operation
	 * @throws java.io.IOException
	 */
	public SmsResponse subscribeReceiveNotifications(ReceiveNotificationSubscribeRequest request) throws IOException {
		ensureRequestValid(request);

		String uri = getServiceBaseUrl() + "/inbound/subscriptions";

		return createAuthenticatedJsonRequest(uri, request)
				.execute(SmsResponse.class);
	}
	
	private TelekomJsonWebRequest createAuthenticatedJsonRequest(String uri,
			ReceiveNotificationSubscribeRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		TelekomJsonWebRequest webRequest = createAuthenticatedRequest(uri,
				HttpMethod.POST);
		
		HashMap<String,HashMap<String, Object>> jsonRequest = new HashMap<String, HashMap<String, Object>>();
		
		HashMap<String,Object> val = new HashMap<String, Object>();
		
		HashMap<String,String> helper = new HashMap<String, String>();
		
		helper.put("notifyURL", request.getNotifyURL());
		if(request.getCallbackData() != null) {
			helper.put("callbackData", request.getCallbackData());
		}
		val.put("callbackReference", helper);
		
		if (request.getCriteria() != null) {
			val.put("criteria", request.getCriteria());
		}
		
		if (request.getClientCorrelator() != null) {
			val.put("clientCorrelator", request.getClientCorrelator());
		}
		
		if (request.getAccount() != null) {
			val.put("account", request.getAccount());
		}
		
		if (request.getNotificationFormat() != null) {
			val.put("notificationFormat", request.getNotificationFormat());
		}
		
		val.put("destinationAddress", request.getDestinationAddress());
		
		jsonRequest.put("subscription", val);
		
		webRequest.setRawContent(JsonSerializer.serializeToBytes(jsonRequest), "application/json; charset=utf-8");
		return webRequest;
	}

}
