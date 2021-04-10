package com.verifycard.core.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardClientResponse<T> {
    private boolean success;
    private int statusCode;
    private String message;
    private T data;
}

