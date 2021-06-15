package com.mplokita.w3w_love.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="google.places.api")
public class GeocodeConfig {

    private String key;

    private String baseURL;

    public String getAPIKey() {
        return key;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setBaseURL(String url) {
        this.baseURL = url;
    }

}
