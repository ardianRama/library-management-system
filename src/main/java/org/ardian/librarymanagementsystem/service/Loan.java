package org.ardian.librarymanagementsystem.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Loan extends JpaRepository<Loan, Long> {
}
