package com.mplokita.w3w_love.model;

import com.fasterxml.jackson.databind.JsonNode;

public class Coordinates {

    private final String latitude;
    private final String longitude;

    public Coordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public static Coordinates fromJsonNode(JsonNode node) {
        JsonNode locationJson = node.get("results")
                .get(0)
                .get("geometry")
                .get("location");
        return new Coordinates(
                locationJson.get("lat").toString(),
                locationJson.get("lng").toString()
        );
    }
}
