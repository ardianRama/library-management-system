package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.model.Loan;

public interface LoanService {

    LoanDto borrowBook(Long userId, Long bookId);
}
