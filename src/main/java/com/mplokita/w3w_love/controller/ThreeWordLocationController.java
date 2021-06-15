package com.mplokita.w3w_love.controller;

import com.mplokita.w3w_love.model.Coordinates;
import com.mplokita.w3w_love.service.GeocodeService;
import com.mplokita.w3w_love.service.ThreeWordLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ThreeWordLocationController {

    private final GeocodeService geocodeService;

    private final ThreeWordLocationService threeWordLocationService;

    public ThreeWordLocationController(
            GeocodeService geocodeService,
            ThreeWordLocationService threeWordLocationService
    ) {
        this.geocodeService = geocodeService;
        this.threeWordLocationService = threeWordLocationService;
    }

    @GetMapping("/words")
    public ResponseEntity<String> getCoordinates(
            @RequestParam(value = "address") String address
    ) {
        Optional<Coordinates> maybeCoordinates = geocodeService.getAddressCoordinates(address);

        if (!maybeCoordinates.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<String> maybeThreeWords = threeWordLocationService.getThreeWordLocation(
                maybeCoordinates.get().getLatitude(),
                maybeCoordinates.get().getLongitude()
        );

        if (!maybeThreeWords.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(maybeThreeWords.get());
    }

}
