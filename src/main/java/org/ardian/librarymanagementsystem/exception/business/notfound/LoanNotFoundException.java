package org.ardian.librarymanagementsystem.exception.business.notfound;

import lombok.Getter;

@Getter
public class LoanNotFoundException extends NotFoundException {

    private final Long bookId;
    private final Long loanId;
    private final String email;

    public LoanNotFoundException(Long bookId, String email) {
        super("No active loan found for user " + email + " and bookId " + bookId);
        this.bookId = bookId;
        this.email = email;
        this.loanId = null;
    }

    public LoanNotFoundException(Long loanId) {
        super("No active loan found with loanId  " + loanId);
        this.loanId = loanId;
        this.bookId = null;
        this.email = null;
    }
}
