package com.verifycard.core.service;

import com.verifycard.core.models.CardClientResponse;
import com.verifycard.core.models.CardResolutionResponse;

public interface CardServiceClient {

    CardClientResponse<CardResolutionResponse> resolveCard(String cardNumber);
}
