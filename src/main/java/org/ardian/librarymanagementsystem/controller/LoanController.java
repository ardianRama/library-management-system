package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.BorrowBookRequest;
import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(@RequestBody BorrowBookRequest request) {
        return ResponseEntity.ok(
                loanService.borrowBook(request.getUserId(), request.getBookId())
        );
    }
}
