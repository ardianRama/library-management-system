package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanService {

    Loan borrowBook(Long userId, String externalId);
}
