package org.ardian.librarymanagementsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for OpenLibrary API.
 * Includes settings such as base URL, cover image URL and default search limits.
 */

@ConfigurationProperties(prefix = "open.library")
@Getter
@Setter
public class OpenLibraryProperties {

    private String baseUrl;
    private String coverBaseUrl;
    private int searchLimit;
}


