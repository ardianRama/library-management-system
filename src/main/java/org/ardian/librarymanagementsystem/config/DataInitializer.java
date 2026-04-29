package org.ardian.librarymanagementsystem.config;

import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initializes development users in the database at application startup.
 * Used for local development/testing.
 */

@Component
public class DataInitializer implements CommandLineRunner {

    private final LibraryUserRepository libraryUserRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String JOHN_EMAIL = "john.doe@gmail.com";
    private static final String ADMIN_EMAIL = "admin@gmail.com";

    private static final String JOHN_PASSWORD = "test1";
    private static final String ADMIN_PASSWORD = "admin";

    public DataInitializer(LibraryUserRepository libraryUserRepository, PasswordEncoder passwordEncoder) {
        this.libraryUserRepository = libraryUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (!libraryUserRepository.existsByEmail(JOHN_EMAIL)) {
            libraryUserRepository.save(
                    LibraryUser.builder()
                            .email(JOHN_EMAIL)
                            .password(passwordEncoder.encode(JOHN_PASSWORD))
                            .firstName("John")
                            .lastName("Doe")
                            .role(Role.USER)
                            .build()
            );
        }

        if (!libraryUserRepository.existsByEmail(ADMIN_EMAIL)) {
            libraryUserRepository.save(
                    LibraryUser.builder()
                            .email(ADMIN_EMAIL)
                            .password(passwordEncoder.encode(ADMIN_PASSWORD))
                            .firstName("Admin")
                            .lastName("Admin")
                            .role(Role.ADMIN)
                            .build()
            );
        }
    }
}
