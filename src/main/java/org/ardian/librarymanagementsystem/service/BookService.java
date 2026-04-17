package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.client.BookClient;
import org.ardian.librarymanagementsystem.config.OpenLibraryProperties;
import org.ardian.librarymanagementsystem.dto.Book;
import org.ardian.librarymanagementsystem.dto.BookDoc;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookClient bookClient;
    private final OpenLibraryProperties properties;

    public BookService(BookClient bookClient, OpenLibraryProperties properties) {
        this.bookClient = bookClient;
        this.properties = properties;
    }

    @Cacheable(value = "books", key = "#query")
    public List<Book> searchBooks(String query) {

        return bookClient.searchBooks(query)
                .stream()
                .map(this::mapToBook)
                .toList();
    }

    private Book mapToBook(BookDoc doc) {

        String author = getAuthor(doc);
        String coverUrl = getCoverUrl(doc.getCoverId());

        return new Book(
                doc.getTitle(),
                author,
                doc.getFirstPublishYear(),
                coverUrl
        );
    }

    private String getAuthor(BookDoc doc) {
        if (doc.getAuthorName() == null || doc.getAuthorName().isEmpty()) {
            return "Unknown";
        }
        return doc.getAuthorName().get(0);
    }

    private String getCoverUrl(Integer coverId) {
        if (coverId == null) {
            return null;
        }

        return properties.getCoverBaseUrl() + coverId + "-M.jpg";
    }
}