package org.ardian.librarymanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent a detailed book in the database/library visible for the admin.
 */

@Getter
@Setter
@Builder
public class BookDetailedDto {

    private Long id;
    private String title;
    private String author;
    private String firstPublishYear;
    private String coverUrl;

    private int totalCopies;
    private int availableCopies;
}
