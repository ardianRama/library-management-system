package org.ardian.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object used when borrowing and returning a book.
 */

@Getter
@Setter
public class BookRequest {

    @NotNull(message = "Book id is required")
    private Long bookId;
}