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

package com.telekom.api.common;

/**
 * Wrapper for Base64 encoding
 */
public class Base64 {

	/**
	 * encode binary data to it's Base64 encoded representation
	 *
	 * @param binaryData
	 *            input data
	 * @return
	 */
	public static String encodeBase64String(byte[] binaryData) {

        String encodedString = android.util.Base64.encodeToString(binaryData, android.util.Base64.DEFAULT);
        String safeString = encodedString.replaceAll("\n","");
        return safeString;
		//return org.apache.commons.codec.binary.Base64
		//		.encodeBase64String(binaryData);
	}

	/**
	 * encode a string to it's Base64 encoded representation
	 *
	 * @param data
	 *            input data
	 * @return
	 */
	public static String encodeBase64String(String data) {
		return encodeBase64String(data.getBytes());
	}

}
