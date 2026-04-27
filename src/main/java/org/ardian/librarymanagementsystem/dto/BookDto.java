package org.ardian.librarymanagementsystem.dto;

import lombok.Getter;

/**
 * Represents a book returned to the client as part of API responses.
 * Mapped from external API documents (BookDoc).
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
