package org.ardian.librarymanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Represents a loan response returned to clients.
 */

@Getter
@Builder
public class LoanDto {

    private Long id;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;

    private Long bookId;
    private String bookTitle;

    private Long libraryUserId;
    private String email;
}
