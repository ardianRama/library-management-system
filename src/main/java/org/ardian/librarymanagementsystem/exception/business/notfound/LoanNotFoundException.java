package org.ardian.librarymanagementsystem.exception.business.notfound;

public class LoanNotFoundException extends NotFoundException {

    public LoanNotFoundException() {
        super("No loan found");
    }
}
