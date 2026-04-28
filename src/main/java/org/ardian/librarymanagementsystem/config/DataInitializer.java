package org.ardian.librarymanagementsystem.config;

import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes default users in the database at application startup.
 */

@Component
public class DataInitializer implements CommandLineRunner {

    private final LibraryUserRepository libraryUserRepository;

    public DataInitializer(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public void run(String... args) {

        if (!libraryUserRepository.existsByUsername("user1")) {
            libraryUserRepository.save(new LibraryUser("user1", "test"));
        }

        if (!libraryUserRepository.existsByUsername("admin")) {
            libraryUserRepository.save(new LibraryUser("admin", "admin"));
        }
    }
}
