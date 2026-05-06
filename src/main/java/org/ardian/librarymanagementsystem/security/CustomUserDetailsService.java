package org.ardian.librarymanagementsystem.security;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Fetches user from database so Spring Security can log in the user.
 */

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LibraryUserRepository libraryUserRepository;

    public CustomUserDetailsService(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        LibraryUser libraryUser = libraryUserRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.debug("Authentication failed - user not found: {}", email);
                    return new UsernameNotFoundException("Invalid credentials");
                });

        return User.builder()
                .username(libraryUser.getEmail())
                .password(libraryUser.getPassword())
                .roles(libraryUser.getRole().name())
                .build();
    }
}
