package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.model.Loan;

public interface LoanService {

    Loan borrowBook(Long userId, Long bookId);
}
