package com.verifycard.infrastructure.restclient.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.verifycard.core.models.CardClientResponse;
import com.verifycard.core.models.CardResolutionResponse;
import com.verifycard.core.service.CardServiceClient;
import com.verifycard.infrastructure.restclient.model.ClientResponse;
import com.verifycard.infrastructure.restclient.service.RestServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service
@RequiredArgsConstructor
public class CardServiceClientImpl implements CardServiceClient {

    @Value("${card-resolution-service-url}")
    private final String baseUrl;

    private final RestServiceClient restServiceClient;
    private final Gson gson;

    @Override
    public CardClientResponse<CardResolutionResponse> resolveCard(String cardNumber) {

        String serviceUrl = String.format("%s/%s", baseUrl, cardNumber);

        try {
            ClientResponse clientResponse = restServiceClient.getRequest(serviceUrl);
            Type responseType = new TypeToken<CardClientResponse<CardResolutionResponse>>() {
            }.getType();
            CardClientResponse<CardResolutionResponse> response = gson.fromJson(clientResponse.getResponseBody(), responseType);
            response.setStatusCode(clientResponse.getStatusCode());
            response.setSuccess(response.getData() != null);
            return response;
        } catch (Exception ex) {
            return CardClientResponse.<CardResolutionResponse>builder().success(false).build();
        }
    }

}
