package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(
            @RequestParam Long userId,
            @RequestParam Long bookId
    ) {
        return ResponseEntity.ok(loanService.borrowBook(userId, bookId));
    }
}
