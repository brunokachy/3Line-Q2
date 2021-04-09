package com.verifycard.entrypoint.models;

import lombok.Data;

@Data
public class BankCardStatResponse {

    private boolean success;

    private long size;

    private int start;

    private int limit;

    private Object payload;

}
