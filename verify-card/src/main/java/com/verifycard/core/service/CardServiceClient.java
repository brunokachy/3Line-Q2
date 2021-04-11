package com.verifycard.core.service;

import com.verifycard.core.models.RestClientResponse;
import com.verifycard.core.models.CardResolutionResponse;

public interface CardServiceClient {

    RestClientResponse<CardResolutionResponse> resolveCard(String cardNumber);
}
