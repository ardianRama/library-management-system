package org.ardian.librarymanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a book used in the application response layer.
 */

@Getter
public class BookDto {
    private String title;
    private String author;
    private String firstPublishYear;
    private String coverUrl;
    private String externalId;

    public BookDto(String title, String author, String firstPublishYear, String coverUrl, String externalId) {
        this.title = title;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.coverUrl = coverUrl;
        this.externalId = externalId;
    }
}
