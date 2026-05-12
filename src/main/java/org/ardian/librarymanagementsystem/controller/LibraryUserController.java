package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.LibraryUserDetailedDto;
import org.ardian.librarymanagementsystem.security.annotation.IsAdmin;
import org.ardian.librarymanagementsystem.service.LibraryUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@IsAdmin
@RestController
@RequestMapping("/api/user")
public class LibraryUserController {

    private final LibraryUserService libraryUserService;

    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping("/detailed")
    public List<LibraryUserDetailedDto> getAllDetailedLibraryUsers() {
        return libraryUserService.getAllUsers();
    }

    @GetMapping("/detailed/{userId}")
    public LibraryUserDetailedDto getDetailedLibraryUser(@PathVariable Long userId) {
        return libraryUserService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteLibraryUser(@PathVariable Long userId) {

        libraryUserService.deleteLibraryUser(userId);

        return ResponseEntity.noContent().build();
    }
}
