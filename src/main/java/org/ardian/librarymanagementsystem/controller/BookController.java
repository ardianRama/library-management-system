package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.UpdateCopiesRequest;
import org.ardian.librarymanagementsystem.model.Book;
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

    //http://localhost:8080/api/books/search?q=harry+potter
    //http://localhost:8080/api/books/search?q=Rowling
    //http://localhost:8080/api/books/search?q=tolkien
    //http://localhost:8080/api/books/search?q=lord+of+the+rings
    //http://localhost:8080/api/books/search?q=history
    //for admin to view books
    @GetMapping("/search")
    public List<BookDto> searchBooks(@RequestParam String q) {
        return bookService.searchBooksFromApi(q);
    }

    //http://localhost:8080/api/books/import?totalCopies=5
    //for admin
    @PostMapping("/import")
    public ResponseEntity<Book> addBook(
            @RequestBody BookDto dto,
            @RequestParam int totalCopies
    ) {
        Book saved = bookService.addBook(dto, totalCopies);
        return ResponseEntity.ok(saved);
    }

    //http://localhost:8080/api/books/OL82586W/copies?totalCopies=25
    @PatchMapping("/{externalId}/copies")
    public ResponseEntity<Book> updateCopies(
            @PathVariable String externalId,
            @RequestBody UpdateCopiesRequest request
    ) {
        return ResponseEntity.ok(
                bookService.updateTotalCopies(externalId, request.getTotalCopies())
        );
    }
}