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

package com.telekom.api.quota;

import com.telekom.api.common.HttpMethod;
import com.telekom.api.common.ServiceEnvironment;
import com.telekom.api.common.TelekomClient;
import com.telekom.api.common.auth.TelekomAuth;
import com.telekom.api.common.model.TelekomResponse;
import com.telekom.api.quota.model.GetAccountBalanceRequest;
import com.telekom.api.quota.model.GetAccountBalanceResponse;
import com.telekom.api.quota.model.GetChargedAmountRequest;
import com.telekom.api.quota.model.GetChargedAmountResponse;
import com.telekom.api.quota.model.GetQuotaInformationResponse;
import com.telekom.api.quota.model.SetQuotaRequest;

import java.io.IOException;

/**
 * Wrapper for Telekom Admin / Quota service
 */
public class QuotaClient extends TelekomClient {

    /**
     * URL Path to Send SMS services. Can be overridden if necessary. %s is
     * replaced by selected environment
     */
    public static String ServicePath = "/plone/odg-admin/rest/%s";

    /**
     * Constructs a Send SMS API client with specified authentication method and
     * environment.
     *
     * @param authentication Authentication instance
     * @param environment    Environment used for this client's service invocations
     */
    public QuotaClient(TelekomAuth authentication,
                       ServiceEnvironment environment) {
        super(authentication, environment, ServicePath);
    }

    /**
     * Query quota information about a service
     *
     * @param service The service and environment to query. One of "SmsProduction",
     *                "SmsSandbox", "MmsProduction", "MmsSandbox",
     *                "VoiceButlerProduction", "VoiceButlerSandbox",
     *                "CCSProduction", "CCSSandbox", "IPLocationProduction",
     *                "IPLocationSandbox"
     * @return
     * @throws java.io.IOException
     */
    public GetQuotaInformationResponse getQuotaInformation(String service)
            throws IOException {
        ensureNotNull(service);

        String uri = getServiceBaseUrl() + "/quotainfo/" + urlEncode(service);

        return createAuthenticatedRequest(uri, HttpMethod.GET).executePatched(
                GetQuotaInformationResponse.class);
    }

    /**
     * Set quota for a specified service
     *
     * @param request Call parameters
     * @return Service call response
     * @throws java.io.IOException
     */
    public TelekomResponse setQuota(SetQuotaRequest request) throws IOException {
        ensureRequestValid(request);

        String uri = getServiceBaseUrl() + "/userquota/"
                + urlEncode(request.getModuleId());

        return createAuthenticatedRequest(uri, HttpMethod.PUT, request)
                .execute(TelekomResponse.class);
    }

    /**
     * Queries the current account balance
     *
     * @param request Call parameters
     * @return Service call response
     * @throws java.io.IOException
     */
    public GetAccountBalanceResponse getAccountBalance(
            GetAccountBalanceRequest request) throws IOException {
        ensureRequestValid(request);

        String uri = getServiceBaseUrl() + "/account/balance";

        return createAuthenticatedRequest(uri, HttpMethod.POST, request)
                .execute(GetAccountBalanceResponse.class);
    }

    /**
     * Queries the charged amount of a conference or voice call
     *
     * @param request Call parameters
     * @return Service call response
     * @throws java.io.IOException
     */
    public GetChargedAmountResponse getChargedAmount(
            GetChargedAmountRequest request) throws IOException {
        ensureRequestValid(request);

        String uri = getServiceBaseUrl() + "/charge/"
                + urlEncode(request.getId());

        return createAuthenticatedRequest(uri, HttpMethod.GET, request)
                .execute(GetChargedAmountResponse.class);
    }

}
