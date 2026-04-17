package org.ardian.librarymanagementsystem.dto;

import lombok.Getter;
import java.util.List;

/**
 * Represents a book used in the application response layer.
 */

@Getter
public class Book {
    private String title;
    private List<String> authors;
    private String firstPublishYear;
    private String coverUrl;
    private String id;

    public Book(String title, List<String> authors, String firstPublishYear, String coverUrl, String id) {
        this.title = title;
        this.authors = authors;
        this.firstPublishYear = firstPublishYear;
        this.coverUrl = coverUrl;
        this.id = id;
    }
}
