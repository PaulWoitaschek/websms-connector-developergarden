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

package com.telekom.api.common.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.telekom.api.common.TelekomParameterException;
import com.telekom.api.common.model.validation.Required;
import com.telekom.api.common.webrequest.IParameterStore;

public class TelekomRequest {

	/**
	 * Use reflection to infer HTTP request parameters
	 * 
	 * @param webRequest
	 *            web request to add parameters
	 */
	public void buildRequestParameters(IParameterStore webRequest) {
		if (webRequest == null)
			throw new RuntimeException("webRequest is null");

		Class<?> cls = (Class<?>) this.getClass();

		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();

			// Prevent adding class name as a request parameter
			if ("getClass".equals(methodName))
				continue;

			if (Modifier.isPublic(method.getModifiers())) {

				// propertyName != null means that the field is serialized
				String propertyName = null;

				// Use all public get...() methods by default
				if (methodName.startsWith("get")) {
					// Convert getCamelCase() method name to "camelCase"
					// property
					propertyName = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);
				}

				Annotation[] annotations = method.getAnnotations();
				for (Annotation annotation : annotations) {
					// Override: Do not serialize
					if (annotation instanceof NoHttpParameter) {
						propertyName = null;
					}
					// Override: Serialize with manual name
					else if (annotation instanceof HttpParameter) {
						propertyName = ((HttpParameter) annotation).name();
					}
				}

				if (propertyName != null) {
					try {
						Object value = method.invoke(this, new Object[] {});

						// Only add defined values
						if (value != null) {
							if (value instanceof Boolean) {
								webRequest
										.addParameter(
												propertyName,
												((Boolean) value)
														.booleanValue() ? "true"
														: "false");
							} else if (value instanceof byte[]) {
								webRequest.addParameter(propertyName,
										(byte[]) value);
							} else {
								webRequest.addParameter(propertyName,
										value.toString());
							}
						}
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}

			}

		}
	}

	/**
	 * Use reflection to check for required fields
	 */
	public void enforceRequiredFields() {

		Class<?> cls = (Class<?>) getClass();

		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();

			if (Modifier.isPublic(method.getModifiers())) {
				try {
					Annotation[] annotations = method.getAnnotations();
					for (Annotation annotation : annotations) {
						if (annotation instanceof Required) {
							Object value = method.invoke(this, new Object[] {});
							if (value == null) {
								throw new TelekomParameterException(
										"Required parameter accessed by "
												+ methodName + " is missing");
							}
						}
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}

		}
	}

}
