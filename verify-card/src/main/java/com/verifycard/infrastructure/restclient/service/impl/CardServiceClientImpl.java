package com.verifycard.infrastructure.restclient.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.verifycard.core.models.RestClientResponse;
import com.verifycard.core.models.CardResolutionResponse;
import com.verifycard.core.service.CardServiceClient;
import com.verifycard.infrastructure.restclient.model.ClientResponse;
import com.verifycard.infrastructure.restclient.service.RestServiceClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service
@RequiredArgsConstructor
public class CardServiceClientImpl implements CardServiceClient {

    @Value("${card-resolution-service-url}")
    private String baseUrl;

    private final RestServiceClient restServiceClient;
    private final Gson gson;

    @Override
    public RestClientResponse<CardResolutionResponse> resolveCard(String cardNumber) {

        String serviceUrl = String.format("%s/%s", baseUrl, cardNumber);

        try {
            ClientResponse clientResponse = restServiceClient.getRequest(serviceUrl);

            RestClientResponse<CardResolutionResponse> response = RestClientResponse.<CardResolutionResponse>builder()
                    .statusCode(clientResponse.getStatusCode())
                    .success(StringUtils.isNotEmpty(clientResponse.getResponseBody()))
                    .build();

            if (response.isSuccess()){
                response.setData(gson.fromJson(clientResponse.getResponseBody(), CardResolutionResponse.class));
            }

            return response;
        } catch (Exception ex) {
            return RestClientResponse.<CardResolutionResponse>builder().success(false).build();
        }
    }

}
