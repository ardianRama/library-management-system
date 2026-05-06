package org.ardian.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.ardian.librarymanagementsystem.dto.BookRequest;
import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.security.annotation.IsUser;
import org.ardian.librarymanagementsystem.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@IsUser
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody BookRequest request) {

        LoanDto saved =
                loanService.borrowBook(user.getUsername(), request.getBookId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/loans/{loanId}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PostMapping("/return")
    public ResponseEntity<LoanDto> returnBook(
            @AuthenticationPrincipal User user,
            @RequestBody BookRequest request
    ) {
        return ResponseEntity.ok(
                loanService.returnBook(user.getUsername(), request.getBookId())
        );
    }

    @GetMapping
    public List<LoanDto> getAllLoans(@AuthenticationPrincipal User user) {
        return loanService.getAllLoans(user.getUsername());
    }

    @GetMapping("/{loanId}")
    public LoanDto getLoanById(
            @AuthenticationPrincipal User user,
            @PathVariable Long loanId
    ) {
        return loanService.getLoanById(loanId, user.getUsername());
    }
}
