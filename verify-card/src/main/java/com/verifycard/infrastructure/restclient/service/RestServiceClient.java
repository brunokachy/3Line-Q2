package com.verifycard.infrastructure.restclient.service;

import com.verifycard.infrastructure.restclient.model.ClientResponse;

public interface RestServiceClient {

    ClientResponse postRequest(String serviceUrl, String requestPayload);
    ClientResponse getRequest(String serviceUrl);
}
