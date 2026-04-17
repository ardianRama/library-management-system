package org.ardian.librarymanagementsystem.client;

import org.ardian.librarymanagementsystem.dto.BookDoc;

import java.util.List;

/**
 * Client interface for retrieving books from an external API.
 */

public interface BookClient {
    List<BookDoc> searchBooks(String query);
}
