package org.ardian.librarymanagementsystem.repository;

import org.ardian.librarymanagementsystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByLibraryUserIdAndBookIdAndReturnedAtIsNull(Long userId, Long bookId);

    Optional<Loan> findByLibraryUserEmailAndBookIdAndReturnedAtIsNull(String email, Long bookId);
}
