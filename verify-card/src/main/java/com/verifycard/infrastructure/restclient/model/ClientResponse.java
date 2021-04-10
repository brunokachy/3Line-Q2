package com.verifycard.infrastructure.restclient.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {
    private String responseBody;
    private int statusCode;
}
