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

public class FormMultipartWriter extends HttpBodyParamWriter {

	private static String MultipartBoundary = "----QcfZx6VDDtDge9X_HdYJkWoDq-ZEbi";

	public FormMultipartWriter(OutputStream stream) {
		super(stream);
	}

	@Override
	public void writeParam(String name, String value) throws IOException {
		if (value != null) {
			writer.println("--" + MultipartBoundary);
			writer.println("Content-Disposition: form-data; name=\"" + name
					+ "\"");
			writer.println("Content-Type: text/plain; charset=UTF-8");
			writer.println("Content-Transfer-Encoding: 8bit");
			writer.println();
			writer.println(value);
		}
	}

	@Override
	public void writeFile(String name, byte[] value) throws IOException {
		writer.println("--" + MultipartBoundary);
		writer.println("Content-Disposition: form-data; name=\"" + name + "\"");
		writer.println("Content-Type: application/octet-stream; charset=ISO-8859-1");
		writer.println("Content-Transfer-Encoding: binary");
		writer.println();
		writer.flush();

		stream.write(value);
		writer.println();
	}

	public static String getContentType() {
		return "multipart/form-data; boundary=" + MultipartBoundary;
	}

	@Override
	public void dispose() throws IOException {
		writer.println("--" + MultipartBoundary + "--");
		super.dispose();
	}

}
