package org.ardian.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.ardian.librarymanagementsystem.dto.*;
import org.ardian.librarymanagementsystem.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * for admin
     */

    //http://localhost:8080/api/books/detailed
    @GetMapping("/detailed")
    public List<BookDetailedDto> getAllDetailedBooks() {
        return bookService.getAllDetailedBooksFromLibrary();
    }

    //http://localhost:8080/api/books/search/external?q=harry+potter
    //http://localhost:8080/api/books/search/external?q=rowling
    @GetMapping("/search/external")
    public List<BookDto> searchBooksExternal(@RequestParam String q) {
        return bookService.searchBooksFromApi(q);
    }

    //http://localhost:8080/api/books/import
    @PostMapping("/import")
    public ResponseEntity<BookDetailedDto> addBook(@RequestBody ImportBookRequest request) {

        BookDetailedDto saved = bookService.addBook(request.getBook(), request.getTotalCopies());

        return ResponseEntity.ok(saved);
    }

    //http://localhost:8080/api/books/452/copies
    @PatchMapping("/{bookId}/copies")
    public ResponseEntity<BookDetailedDto> updateCopies(
            @PathVariable Long bookId,
            @Valid @RequestBody UpdateCopiesRequest request
    ) {
        return ResponseEntity.ok(
                bookService.updateTotalCopies(bookId, request.getTotalCopies())
        );
    }

    //http://localhost:8080/api/books/452
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }

    /**
     * for user
     */

    //http://localhost:8080/api/books
    @GetMapping
    public List<LibraryBookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    //http://localhost:8080/api/books/search?q=tolkien
    //http://localhost:8080/api/books/search?q=lord+of+the+rings
    @GetMapping("/search")
    public List<LibraryBookDto> searchBooks(@RequestParam String q) {
        return bookService.searchBooksInLibrary(q);
    }
}