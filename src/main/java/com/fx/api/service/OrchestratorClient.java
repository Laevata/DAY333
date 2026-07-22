package com.fx.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Component
public class OrchestratorClient {

    private static final Logger log = LoggerFactory.getLogger(OrchestratorClient.class);

    private final RestTemplate http;
    private final String baseUrl;

    public OrchestratorClient(RestTemplate http,
                              @Value("${fx.orchestrator.url}") String baseUrl) {
        this.http = http;
        this.baseUrl = trimSlash(baseUrl);
    }

    public void ack(long batchId, String status) {
        String url = baseUrl + "/api/feed/ack";
        Map<String, Object> body = Map.of("batchId", batchId, "status", status);

        try {
            RequestEntity<Map<String, Object>> req = RequestEntity
                    .post(URI.create(url))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body);
            http.exchange(req, Void.class);
        } catch (RuntimeException e) {
            // A failed callback must not break the ingestion request path.
            log.warn("Ack callback failed for batch {} status {}: {}", batchId, status, e.getMessage());
        }
    }

    private static String trimSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}

