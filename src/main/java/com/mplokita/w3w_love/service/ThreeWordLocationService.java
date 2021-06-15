package com.mplokita.w3w_love.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mplokita.w3w_love.configuration.ThreeWordLocationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class ThreeWordLocationService {

    private final WebClient requester;

    private final ThreeWordLocationConfig config;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(ThreeWordLocationService.class);

    public ThreeWordLocationService(
            ThreeWordLocationConfig config,
            WebClient.Builder requesterBuilder
    ) {
        this.config = config;
        this.requester = requesterBuilder.baseUrl(config.getBaseURL()).build();
    }

    public Optional<String> getThreeWordLocation(String lat, String lng) {
        String uri = UriComponentsBuilder
                .fromUriString(config.getBaseURL())
                .path(config.getVersion())
                .path("/convert-to-3wa")
                .queryParam("coordinates", String.format("%s,%s", lat, lng))
                .queryParam("key", config.getAPIKey())
                .build()
                .toUriString();

        return requester
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnError(err ->  {
                    log.warn(err.getMessage().replaceAll(config.getAPIKey(), "API_KEY"));
                })
                .onErrorReturn(WebClientResponseException.class, mapper.nullNode())
                .filter(it -> !it.isNull())
                .map(it -> it.get("words").toString())
                .blockOptional();
    }

}
