package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.LoanDto;

public interface LoanService {

    LoanDto borrowBook(Long bookId);
}
