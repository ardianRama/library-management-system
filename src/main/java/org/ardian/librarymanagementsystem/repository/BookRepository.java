package org.ardian.librarymanagementsystem.repository;

import org.ardian.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for Book entity database operations.
 */

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByExternalId(String externalId);

    Optional<Book> findByExternalId(String externalId);

    List<Book> findByTitleOrAuthor(String title, String author);
}
