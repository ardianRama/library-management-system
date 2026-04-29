package org.ardian.librarymanagementsystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Represents a request used when importing a book into the library system.
 */

@Getter
public class ImportBookRequest {

    @NotNull(message = "Book data is required")
    private BookDto book;

    @Min(value = 1, message = "Total copies must be at least 1")
    private int totalCopies;
}
