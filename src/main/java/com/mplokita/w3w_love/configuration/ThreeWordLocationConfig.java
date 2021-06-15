package com.mplokita.w3w_love.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="what3words.api")
public class ThreeWordLocationConfig {

    private String key;

    private String baseURL;

    private String version;

    public String getAPIKey() {
        return key;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getVersion() { return version; }

    public void setKey(String key) {
        this.key = key;
    }

    public void setBaseURL(String url) {
        this.baseURL = url;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
