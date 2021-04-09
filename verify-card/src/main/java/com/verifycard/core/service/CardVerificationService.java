package com.verifycard.core.service;

import com.verifycard.core.models.BankCard;

public interface CardVerificationService {

    BankCard verifyCard(String cardNumber);
}
