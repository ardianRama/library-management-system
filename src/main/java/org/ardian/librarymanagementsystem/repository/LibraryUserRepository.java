package org.ardian.librarymanagementsystem.repository;

import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for LibraryUser entity database operations.
 */

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {

    boolean existsByEmail(String email);
}
