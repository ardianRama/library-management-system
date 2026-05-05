package org.ardian.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.service.LibraryUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LibraryUserService libraryUserService;

    public AuthController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody LibraryUserDto dto) {

        libraryUserService.registerUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}