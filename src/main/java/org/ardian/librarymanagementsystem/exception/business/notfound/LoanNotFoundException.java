package org.ardian.librarymanagementsystem.exception.business.notfound;

import lombok.Getter;

@Getter
public class LoanNotFoundException extends NotFoundException {

    private final Long id;
    private final String email;

    public LoanNotFoundException(Long id, String email) {
        super("No active loan found for user " + email + " and bookId " + id);
        this.id = id;
        this.email = email;
    }

    public LoanNotFoundException(Long id) {
        super("No loan found with loanId  " + id);
        this.id = null;
        this.email = null;
    }
}
