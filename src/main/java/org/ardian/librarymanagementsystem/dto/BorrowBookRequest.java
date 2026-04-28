package org.ardian.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Request object used when borrowing a book.
 */

@Getter
@Setter
public class BorrowBookRequest {

    private Long userId;
    private Long bookId;
}