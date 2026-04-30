package org.ardian.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.ardian.librarymanagementsystem.dto.BorrowBookRequest;
import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    //http://localhost:8080/api/loans/borrow
    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody BorrowBookRequest request
    ) {
        return ResponseEntity.ok(
                loanService.borrowBook(user.getUsername(), request.getBookId())
        );
    }
}
