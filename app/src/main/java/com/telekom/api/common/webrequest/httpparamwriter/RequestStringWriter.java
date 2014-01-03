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

package com.telekom.api.common.webrequest.httpparamwriter;

import java.io.IOException;
import java.io.OutputStream;

import com.telekom.api.common.UriHelper;

public class RequestStringWriter extends HttpBodyParamWriter {

	private boolean first = true;

	public RequestStringWriter(OutputStream stream) {
		super(stream);
	}

	@Override
	public void writeParam(String key, String value) throws IOException {
		// Parameter separation
		if (first) {
			first = false;
		} else {
			writer.write('&');
		}

		writer.write(UriHelper.escapeDataString(key) + "="
				+ UriHelper.escapeDataString(value));
	}

	@Override
	public void writeFile(String key, byte[] value) throws IOException {
		throw new RuntimeException("Not supported");
	}

	// @Override
	public static String getContentType() {
		return "application/x-www-form-urlencoded";
	}

}
