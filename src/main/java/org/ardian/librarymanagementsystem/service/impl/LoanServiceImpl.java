package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.repository.BookRepository;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.ardian.librarymanagementsystem.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl {

    private final BookRepository bookRepository;
    private final LibraryUserRepository libraryUserRepository;
    private final LoanRepository loanRepository;

    public LoanServiceImpl(BookRepository bookRepository, LibraryUserRepository libraryUserRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.libraryUserRepository = libraryUserRepository;
        this.loanRepository = loanRepository;
    }
}
