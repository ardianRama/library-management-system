package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.Book;
import org.ardian.librarymanagementsystem.service.BookService;
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
    @GetMapping("/search")
    public List<Book> search(@RequestParam String q) {
        return bookService.searchBooks(q);
    }

}