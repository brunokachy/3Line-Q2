package com.verifycard.core.service;

import com.verifycard.entrypoint.models.BankCardVerificationResponse;

public interface CardVerificationService {

    BankCardVerificationResponse verifyCard(String cardNumber);
}
