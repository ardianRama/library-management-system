package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.LoanDto;

import java.util.List;

public interface LoanService {

    LoanDto borrowBook(String email, Long bookId);

    LoanDto returnBook(String email, Long bookId);

    List<LoanDto> getAllLoans();

    LoanDto getLoanById(Long loanId, String email);
}
