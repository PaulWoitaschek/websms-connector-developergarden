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

import com.telekom.api.common.HttpMethod;
import com.telekom.api.common.TelekomConfig;
import com.telekom.api.common.TelekomException;
import com.telekom.api.common.webrequest.httpparamwriter.FormMultipartWriter;
import com.telekom.api.common.webrequest.httpparamwriter.HttpBodyParamWriter;
import com.telekom.api.common.webrequest.httpparamwriter.RequestStringWriter;
import com.telekom.api.common.webrequest.httpparamwriter.UriParameterBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class TelekomWebRequest implements IParameterStore {

	protected HttpsURLConnection connection;
	protected Map<String, String> bodyParameter;
	protected Map<String, byte[]> attachedFiles;
	protected UriParameterBuilder uriParamBuilder;
	
	protected Map<String, String> additionalHeaders;

	private byte[] rawContent;
	private String rawContentType;

	protected String uri;
	private HttpMethod method;
	private String userAgent;
	private String accept;
	protected String authHeader;

	public void setAuthHeader(String authHeader) {
		this.authHeader = authHeader;
	}

	public TelekomWebRequest(String uri, HttpMethod method) {
		this.uri = uri;
		this.method = method;

		userAgent = TelekomConfig.UserAgent;
		accept = "application/json";
		authHeader = null;

		bodyParameter = new HashMap<String, String>();
		attachedFiles = new HashMap<String, byte[]>();
		uriParamBuilder = new UriParameterBuilder();
	}

	/**
	 * @see com.telekom.api.common.webrequest.IParameterStore#addParameter(String,
	 *      String)
	 */
	public void addParameter(String key, String value) {
		if (method == HttpMethod.GET) {
			uriParamBuilder.writeParam(key, value);
		} else {
			bodyParameter.put(key, value);
		}
	}

	/**
	 * @see com.telekom.api.common.webrequest.IParameterStore#addParameter(String,
	 *      byte[])
	 */
	public void addParameter(String key, byte[] data) {
		if (method == HttpMethod.GET)
			throw new TelekomException("Cannot add files to GET request");

		attachedFiles.put(key, data);
	}

	public void setRawContent(byte[] rawData, String contentType) {
		this.rawContent = rawData;
		this.rawContentType = contentType;
	}

	public WebResponse executeRaw() {

		String uriWithParameters = uri + uriParamBuilder.toString();

		try {
			connection = (HttpsURLConnection) (new URL(uriWithParameters))
					.openConnection();
			connection.setRequestMethod(method.toString());
			connection.setRequestProperty("User-Agent", userAgent);
			connection.setRequestProperty("Accept", accept);
			if (authHeader != null) {
				connection.setRequestProperty("Authorization", authHeader);
			}
			connection.setAllowUserInteraction(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			writePayload();

			return new WebResponse(connection);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public WebResponse executeRaw(boolean custom) {

		String uriWithParameters = uri + uriParamBuilder.toString();

		try {
			connection = (HttpsURLConnection) (new URL(uriWithParameters))
					.openConnection();
			connection.setRequestMethod(method.toString());
			connection.setRequestProperty("User-Agent", userAgent);
			connection.setRequestProperty("Accept", accept);
			if (authHeader != null) {
				connection.setRequestProperty("Authorization", authHeader);
			}
			if (custom) {
				for (String hashKey : this.additionalHeaders.keySet()) {
					connection.setRequestProperty(hashKey, this.additionalHeaders.get(hashKey));
				}
			}
				
			connection.setAllowUserInteraction(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			writePayload();

			return new WebResponse(connection);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writePayload() throws IOException {
		if (rawContent != null) {
			connection.setRequestProperty("Content-Type", rawContentType);
			connection.getOutputStream().write(rawContent);
		} else if ((bodyParameter.size() > 0) || (attachedFiles.size() > 0)) {
			HttpBodyParamWriter paramWriter;
			// If files have to be attached, use Form Multipart format
			if (attachedFiles.size() > 0) {
				connection.setRequestProperty("Content-Type",
						FormMultipartWriter.getContentType());
				paramWriter = new FormMultipartWriter(
						connection.getOutputStream());
			} else {
				connection.setRequestProperty("Content-Type",
						RequestStringWriter.getContentType());
				paramWriter = new RequestStringWriter(
						connection.getOutputStream());
			}
			writeBodyParameter(paramWriter);
			paramWriter.dispose();
		} else {
			// No parameter
			connection.connect();
		}
	}

	private void writeBodyParameter(HttpBodyParamWriter paramWriter)
			throws IOException {
		for (Map.Entry<String, String> entry : bodyParameter.entrySet()) {
			paramWriter.writeParam(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, byte[]> entry : attachedFiles.entrySet()) {
			paramWriter.writeFile(entry.getKey(), entry.getValue());
		}
	}

}
