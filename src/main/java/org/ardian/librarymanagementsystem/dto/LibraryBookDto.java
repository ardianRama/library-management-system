package org.ardian.librarymanagementsystem.dto;

/**
 *  Represent a book from the database/library.
 */

public class LibraryBookDto {

    private Long id;
    private String title;
    private String author;
    private String firstPublishYear;
    private String coverUrl;

    private int totalCopies;
    private int availableCopies;
}
