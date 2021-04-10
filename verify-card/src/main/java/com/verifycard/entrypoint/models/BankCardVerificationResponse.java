package com.verifycard.entrypoint.models;

import lombok.Data;

@Data
public class BankCardVerificationResponse {

    private boolean success;

    private PayLoad payload;

    @Data
    public static class PayLoad{
        private String bank;

        private String type;

        private String scheme;
    }
}
