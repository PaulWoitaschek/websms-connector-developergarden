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
import java.io.InputStreamReader;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer {

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		// Do not include properties that are set to null
		mapper.setSerializationInclusion(Include.NON_NULL);

		// Although all JSON properties are reflected in the class structure,
		// the introduction of new properties should not raise exceptions
		// which would force applications to update the SDK.
		// Comment out the following line during SDK development:
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	}

	public static <T> T deserialize(String jsonAsString, Class<T> valueType)
			throws IOException {

		// Workaround: Before the call is established, the VoiceCall backend
		// returns an object instead of an
		// expected value type
		jsonAsString = jsonAsString.replace("{\"@nil\":\"true\"}", "null");

		return mapper.readValue(jsonAsString, valueType);
	}

	public static <T> T deserialize(InputStream stream, Class<T> valueType)
			throws IOException {

		// Convert stream to string first , because the string deserialization
		// routine
		// contains a workaround
		InputStreamReader sr = new InputStreamReader(stream);
		StringBuilder sbBuilder = new StringBuilder();
		char[] buff = new char[1024];
		while (true) {
			int charsRead = sr.read(buff, 0, buff.length);

			if (charsRead == -1)
				break;

			sbBuilder.append(buff, 0, charsRead);
		}
		String content = sbBuilder.toString();

		return deserialize(content, valueType);
		// return mapper.readValue(stream, valueType);
	}

	public static String serialize(Object o) throws IOException {
		return mapper.writeValueAsString(o);
	}

	public static byte[] serializeToBytes(Object o) throws IOException {
		return mapper.writeValueAsBytes(o);
	}

}
