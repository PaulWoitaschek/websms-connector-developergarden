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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.telekom.api.common.HttpMethod;
import com.telekom.api.common.ServiceEnvironment;
import com.telekom.api.common.TelekomClient;
import com.telekom.api.common.auth.TelekomAuth;
import com.telekom.api.common.model.SmsResponse;
import com.telekom.api.common.webrequest.TelekomJsonWebRequest;
import com.telekom.api.sendsms.model.QueryReportRequest;

public class QueryReportClient extends TelekomClient {

	/**
	 * URL Path to Send SMS Query Report services. Can be overridden if necessary. %s is
	 * replaced by selected environment
	 */
	public static String ServicePath = "/plone/sms/rest/%s/smsmessaging/v1";
	
	/**
	 * Constructs a Send SMS API Query Report client with specified authentication method and
	 * environment.
	 * 
	 * @param authentication
	 *            Authentication instance
	 * @param environment
	 *            Environment used for this client's service invocations
	 */
	public QueryReportClient(TelekomAuth authentication,
			ServiceEnvironment environment) {
		super(authentication, environment, ServicePath);
	}
	
	/**
	 * Query the Delivery Report of an SMS
	 * 
	 * @param request
	 *            Parameter object
	 * @return Result of this operation
	 * @throws java.io.IOException
	 */
	public SmsResponse queryReport(QueryReportRequest request, String reportId) throws IOException {
		ensureRequestValid(request);

		String uri = getServiceBaseUrl() + "/outbound/%s/requests";
		uri = String.format(uri, urlEncode(request.getSenderAddress()));
		String tmp = "/%s/deliveryInfos";
		uri = uri + String.format(tmp, urlEncode(reportId));

		return createAuthenticatedJsonRequest(uri, request)
				.execute(SmsResponse.class);
	}
	
	private TelekomJsonWebRequest createAuthenticatedJsonRequest(String uri,
			QueryReportRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		TelekomJsonWebRequest webRequest = createAuthenticatedRequest(uri,
				HttpMethod.GET);
		webRequest.setRawContent(null, "application/json; charset=utf-8");
		return webRequest;
	}

}
