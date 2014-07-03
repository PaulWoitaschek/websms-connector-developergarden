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
 * Response that contains the charged amount of a voice or conference call
 */
public class GetChargedAmountResponse extends TelekomResponse {

    private Integer charge;

    public GetChargedAmountResponse(
            @JsonProperty(value = "charge") Integer charge) {
        this.charge = charge;
    }

    /**
     * @return Current cost amount of the requested id
     */
    public Integer getCharge() {
        return charge;
    }

}
