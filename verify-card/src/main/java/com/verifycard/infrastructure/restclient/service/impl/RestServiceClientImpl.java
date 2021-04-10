package com.verifycard.infrastructure.restclient.service.impl;

import com.verifycard.infrastructure.restclient.model.ClientResponse;
import com.verifycard.infrastructure.restclient.service.RestServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
public class RestServiceClientImpl implements RestServiceClient {

    private final RestTemplate restTemplate;

    @Override
    public ClientResponse postRequest(String serviceUrl, String requestPayload) {
        ResponseEntity<String> responseEntity = postRequest(requestPayload, serviceUrl, createRequestHeader());
        return ClientResponse.builder()
                .statusCode(responseEntity.getStatusCodeValue())
                .responseBody(StringUtils.defaultString(responseEntity.getBody()))
                .build();
    }

    @Override
    public ClientResponse getRequest(String serviceUrl) {
        ResponseEntity<String> responseEntity = getRequest(serviceUrl, createRequestHeader());
        return ClientResponse.builder()
                .statusCode(responseEntity.getStatusCodeValue())
                .responseBody(StringUtils.defaultString(responseEntity.getBody()))
                .build();
    }

    private ResponseEntity<String> postRequest(String requestPayload, String serviceUrl, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);
        try {
            return restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, String.class);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception exception) {
            log.error("Exception: {}", ExceptionUtils.getStackTrace(exception));
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    private ResponseEntity<String> getRequest(String serviceUrl, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(serviceUrl, HttpMethod.GET, entity, String.class);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception exception) {
            log.error("Exception: {}", ExceptionUtils.getStackTrace(exception));
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
