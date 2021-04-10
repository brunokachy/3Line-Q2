package com.verifycard.core.models;

import lombok.Data;

@Data
public class CardResolutionResponse {

    private Bank bank;

    private String scheme;

    private String type;

    private String brand;

    private boolean prepaid;

    @Data
    public static class Bank {
        private String name;

        private String url;

        private String phone;

        private String city;

    }
}
