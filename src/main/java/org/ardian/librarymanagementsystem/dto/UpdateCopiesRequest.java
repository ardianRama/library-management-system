package org.ardian.librarymanagementsystem.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

/**
 * Represents a request to update the total number of copies
 * of a book in the library system.
 */

@Getter
public class UpdateCopiesRequest {

    @Min(value = 1, message = "Total copies must be at least 1")
    private int totalCopies;
}
