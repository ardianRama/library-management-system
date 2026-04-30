package org.ardian.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object used when borrowing a book.
 */

@Getter
@Setter
public class BorrowBookRequest {

    @NotNull(message = "Book id is required")
    private Long bookId;
}