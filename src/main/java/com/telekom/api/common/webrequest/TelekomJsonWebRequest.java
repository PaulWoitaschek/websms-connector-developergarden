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

package com.telekom.api.common.webrequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.telekom.api.common.HttpMethod;

/**
 * Web Request whose response will be deserialized to the specified
 * ResponseType. Response format is JSON, ResponseType class must be decorated
 * as DataContract.
 */
public class TelekomJsonWebRequest extends TelekomWebRequest {

	public TelekomJsonWebRequest(String uri, HttpMethod method) {
		super(uri, method);
	}

	public <ResponseType> ResponseType execute(Class<ResponseType> valueType)
			throws IOException {
		WebResponse response = executeRaw();
		InputStream responseStream = response.getResponseStream();
		
		return JsonSerializer.deserialize(responseStream, valueType);
	}
	
	public <ResponseType> ResponseType executeCustom(Class<ResponseType> valueType, HashMap<String, String> additionalHeaders)
			throws IOException {
		this.additionalHeaders = additionalHeaders;
		
		WebResponse response = executeRaw(true);
		InputStream responseStream = response.getResponseStream();
		
		return JsonSerializer.deserialize(responseStream, valueType);
	}

}
