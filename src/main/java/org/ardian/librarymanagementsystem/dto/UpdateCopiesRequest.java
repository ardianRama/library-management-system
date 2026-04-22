package org.ardian.librarymanagementsystem.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request to update the total number of copies
 * of a book in the library system.
 */

@Getter
@Setter
public class UpdateCopiesRequest {

    @Min(value = 1)
    private int totalCopies;
}
