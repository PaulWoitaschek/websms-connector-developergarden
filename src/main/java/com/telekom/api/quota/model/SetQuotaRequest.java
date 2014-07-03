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

package com.telekom.api.quota.model;

import com.telekom.api.common.model.NoHttpParameter;
import com.telekom.api.common.model.TelekomRequest;
import com.telekom.api.common.model.validation.Required;

/**
 * Parameters to change the quota of a service
 */
public class SetQuotaRequest extends TelekomRequest {

    private String moduleId;

    private Integer value;

    @NoHttpParameter
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId Service and environment to change quota for One of
     *                 "SmsProduction", "SmsSandbox", "VoiceButlerProduction",
     *                 "VoiceButlerSandbox", "CCSProduction", "CCSSandbox",
     *                 "IPLocationProduction", "IPLocationSandbox"
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Required
    public Integer getValue() {
        return value;
    }

    /**
     * @param value The new maximum available quota per day
     */
    public void setValue(Integer value) {
        this.value = value;
    }

}
