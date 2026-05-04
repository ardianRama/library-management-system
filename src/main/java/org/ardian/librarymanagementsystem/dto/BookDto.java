package org.ardian.librarymanagementsystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * Represents a book returned to the client as part of API responses.
 * Mapped from external API documents (BookDoc).
 */

@Getter
public class BookDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private Integer firstPublishYear;

    private String coverUrl;

    @NotBlank
    private String externalId;

    public BookDto(String title, String author, Integer firstPublishYear, String coverUrl, String externalId) {
        this.title = title;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.coverUrl = coverUrl;
        this.externalId = externalId;
    }
}
