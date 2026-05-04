package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.LibraryUserDetailedDto;
import org.ardian.librarymanagementsystem.service.LibraryUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class LibraryUserController {

    private final LibraryUserService libraryUserService;

    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    /**
     *  for admin
     */

    @GetMapping("/detailed")
    public List<LibraryUserDetailedDto> getAllDetailedLibraryUsers() {
        return libraryUserService.getUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteLibraryUser(@PathVariable Long userId) {

        libraryUserService.deleteLibraryUser(userId);

        return ResponseEntity.noContent().build();
    }
}
