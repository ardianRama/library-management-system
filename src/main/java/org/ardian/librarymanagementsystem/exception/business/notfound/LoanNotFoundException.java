package org.ardian.librarymanagementsystem.exception.business.notfound;

import lombok.Getter;

@Getter
public class LoanNotFoundException extends NotFoundException {

    private final Long bookId;
    private final String email;

    public LoanNotFoundException(Long bookId, String email) {
        super("No active loan found for user " + email + " and bookId " + bookId);
        this.bookId = bookId;
        this.email = email;
    }
}
