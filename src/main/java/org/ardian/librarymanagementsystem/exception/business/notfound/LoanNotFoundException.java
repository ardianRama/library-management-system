package org.ardian.librarymanagementsystem.exception.business.notfound;

public class LoanNotFoundException extends NotFoundException {

    public LoanNotFoundException(Long bookId, String email) {
        super("No active loan found for user '" + email + "' and bookId " + bookId);
    }

    public LoanNotFoundException(Long loanId) {
        super("No loan found with loanId " + loanId);
    }
}
