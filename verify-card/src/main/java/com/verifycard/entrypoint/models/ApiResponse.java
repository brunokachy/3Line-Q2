package com.verifycard.entrypoint.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse(String message) {
        this.message = message;
        this.data = null;
    }
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}