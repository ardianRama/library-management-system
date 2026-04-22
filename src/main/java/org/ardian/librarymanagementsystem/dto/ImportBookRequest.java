package org.ardian.librarymanagementsystem.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

/**
 * Represents a request used when importing a book into the library system.
 */

@Getter
public class ImportBookRequest {

    private BookDto book;

    @Min(1)
    private int totalCopies;
}
