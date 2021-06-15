package com.mplokita.w3w_love.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mplokita.w3w_love.configuration.GeocodeConfig;
import com.mplokita.w3w_love.model.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class GeocodeService {

    private final WebClient requester;

    private final GeocodeConfig config;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(GeocodeService.class);

    public GeocodeService(WebClient.Builder requesterBuilder, GeocodeConfig config) {
        this.config = config;
        this.requester = requesterBuilder.baseUrl(config.getBaseURL()).build();
    }

    public Optional<Coordinates> getAddressCoordinates(String address) {
        String uri = UriComponentsBuilder
                .fromUriString(config.getBaseURL())
                .path("/maps/api/geocode")
                .path("/json")
                .queryParam("address", address)
                .queryParam("key", config.getAPIKey())
                .build()
                .toUriString();

        return requester
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnError(err -> log.warn(err.getMessage().replaceAll(config.getAPIKey(), "API_KEY")))
                .onErrorReturn(WebClientResponseException.class, mapper.nullNode())
                .filter(it -> !it.isNull())
                .map(Coordinates::fromJsonNode)
                .blockOptional();
    }

}
