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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telekom.api.common.model.TelekomResponse;

/**
 * Service response when fetching quota information
 */
public class GetQuotaInformationResponse extends TelekomResponse {

    private int maxQuota;

    private int maxUserQuota;

    private int quotaLevel;

    public GetQuotaInformationResponse(
            @JsonProperty(value = "maxQuota") int maxQuota,
            @JsonProperty(value = "maxUserQuota") int maxUserQuota,
            @JsonProperty(value = "quotaLevel") int quotaLevel) {
        this.maxQuota = maxQuota;
        this.maxUserQuota = maxUserQuota;
        this.quotaLevel = quotaLevel;
    }

    /**
     * @return System-defined limit of maximum quota points that can be consumed
     * per day. This system quota cannot be exceeded by the user quota
     * (maxUserQuota).
     */
    public int getMaxQuota() {
        return maxQuota;
    }

    /**
     * @return User-defined limit of maximum quota points that can be consumed
     * per day. This user quota cannot exceed the system quota
     * (maxQuota).
     */
    public int getMaxUserQuota() {
        return maxUserQuota;
    }

    /**
     * @return Number of quota points consumed today.
     */
    public int getQuotaLevel() {
        return quotaLevel;
    }

}
