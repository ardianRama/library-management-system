package org.ardian.librarymanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LoanDto {

    private Long id;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;

    private Long bookId;
    private String bookTitle;

    private Long libraryUserId;
    private String username;
}
