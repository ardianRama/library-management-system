package org.ardian.librarymanagementsystem.dto;

import lombok.Getter;

/**
 * Represents a book used in the application response layer.
 */

@Getter
public class Book {
    private String title;
    private String author;
    private String firstPublishYear;
    private String coverUrl;

    public Book(String title, String author, String firstPublishYear, String coverUrl) {
        this.title = title;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.coverUrl = coverUrl;
    }
}
