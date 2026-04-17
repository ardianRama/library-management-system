package org.ardian.librarymanagementsystem.client;

import org.ardian.librarymanagementsystem.config.OpenLibraryProperties;
import org.ardian.librarymanagementsystem.dto.BookDoc;
import org.ardian.librarymanagementsystem.dto.SearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * OpenLibrary client implementation for searching books.
 */

@Service
public class OpenLibraryBookClient implements BookClient {

    private final WebClient webClient;
    private final OpenLibraryProperties properties;

    public OpenLibraryBookClient(WebClient webClient,
                                 OpenLibraryProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    @Override
    public List<BookDoc> searchBooks(String query) {

        SearchResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.json")
                        .queryParam("q", query)
                        .queryParam("limit", properties.getSearchLimit())
                        .build())
                .retrieve()
                .bodyToMono(SearchResponse.class)
                .block();

        if (response == null || response.getDocs() == null) {
            return List.of();
        }

        return response.getDocs();
    }
}