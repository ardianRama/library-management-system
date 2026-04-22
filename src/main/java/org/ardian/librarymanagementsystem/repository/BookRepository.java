package org.ardian.librarymanagementsystem.repository;

import org.ardian.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for Book entity database operations.
 */

public interface BookRepository extends JpaRepository<Book, Long> {
}
