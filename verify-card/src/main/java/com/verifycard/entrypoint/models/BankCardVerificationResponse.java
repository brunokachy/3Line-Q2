package com.verifycard.entrypoint.models;

import com.verifycard.core.models.BankCard;
import lombok.Data;

@Data
public class BankCardVerificationResponse {

    private boolean success;

    private BankCard payload;
}
