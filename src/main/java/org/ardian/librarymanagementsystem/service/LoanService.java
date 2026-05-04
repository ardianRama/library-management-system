package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.LoanDto;

public interface LoanService {

    LoanDto borrowBook(String email, Long bookId);

    LoanDto returnBook(String email, Long bookId);
}
