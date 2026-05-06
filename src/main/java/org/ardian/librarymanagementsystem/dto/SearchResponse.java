package org.ardian.librarymanagementsystem.dto;

import lombok.Getter;

import java.util.List;

/**
 * DTO for mapping the OpenLibrary search API response.
 */

@Getter
public class SearchResponse {
    private List<BookDoc> docs;
}
