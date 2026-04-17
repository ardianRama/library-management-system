package org.ardian.librarymanagementsystem.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for OpenLibrary WebClient.
 * Defines a shared WebClient bean configured with the base URL
 * for the OpenLibrary API. This client is used for all external
 * requests to fetch book data from OpenLibrary.
 */

@Configuration
@EnableConfigurationProperties(OpenLibraryProperties.class)
public class WebClientConfig {

    private final OpenLibraryProperties properties;

    public WebClientConfig(OpenLibraryProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WebClient openLibraryClient() {
        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
