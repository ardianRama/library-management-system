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

        if (!libraryUserRepository.existsByEmail("john.doe@gmail.com")) {
            libraryUserRepository.save(
                    LibraryUser.builder()
                            .email("john.doe@gmail.com")
                            .password("test1")
                            .firstName("John")
                            .lastName("Doe")
                            .build()
            );
        }

        if (!libraryUserRepository.existsByEmail("admin@gmail.com")) {
            libraryUserRepository.save(
                    LibraryUser.builder()
                            .email("admin@gmail.com")
                            .password("admin")
                            .firstName("Admin")
                            .lastName("Admin")
                            .build()
            );
        }
    }
}
