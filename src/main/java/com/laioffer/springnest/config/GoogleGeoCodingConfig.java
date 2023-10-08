package com.laioffer.springnest.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Setup for using the Google Geocoding API.
@Configuration
public class GoogleGeoCodingConfig {


    @Value("${geocoding.apikey}")
    private String apiKey;


    // Create an instance of GeoApiContext
    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder().apiKey(apiKey).build();
    }
}

