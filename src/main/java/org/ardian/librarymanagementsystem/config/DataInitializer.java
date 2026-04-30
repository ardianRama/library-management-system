package org.ardian.librarymanagementsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Creates an initial admin account when the application starts.
 */

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final LibraryUserRepository libraryUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    public DataInitializer(
            LibraryUserRepository libraryUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.libraryUserRepository = libraryUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (!libraryUserRepository.existsByEmail(adminEmail)) {

            log.info("Creating initial admin account with email: {}", adminEmail);

            libraryUserRepository.save(
                    LibraryUser.builder()
                            .email(adminEmail)
                            .password(passwordEncoder.encode(adminPassword))
                            .firstName("Admin")
                            .lastName("Admin")
                            .role(Role.ADMIN)
                            .build()
            );
        }
    }
}
