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

import com.telekom.api.common.HttpMethod;
import com.telekom.api.common.ServiceEnvironment;
import com.telekom.api.common.TelekomClient;
import com.telekom.api.common.auth.TelekomAuth;
import com.telekom.api.common.model.SmsResponse;
import com.telekom.api.sendsms.model.NotificationUnsubscribeRequest;

public class NotificationUnsubscribeClient extends TelekomClient {

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
	public NotificationUnsubscribeClient(TelekomAuth authentication,
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
	public SmsResponse unsubscribeNotifications(NotificationUnsubscribeRequest request) throws IOException {
		ensureRequestValid(request);

		String uri = getServiceBaseUrl() + "/outbound/subscriptions/%s";
		
		uri = String.format(uri, request.getSubscriptionId());

		return createAuthenticatedRequest(uri, HttpMethod.DELETE, request)
				.execute(SmsResponse.class);
	}

}

