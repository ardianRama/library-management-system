package org.ardian.librarymanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *  Represent a book from the database/library visible for the user.
 */

@Getter
@Setter
@Builder
public class LibraryBookDto {

    private Long id;
    private String title;
    private String author;
    private Integer firstPublishYear;
    private String coverUrl;

    private int availableCopies;
}
