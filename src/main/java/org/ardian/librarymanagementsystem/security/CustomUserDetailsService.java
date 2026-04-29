package org.ardian.librarymanagementsystem.security;

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

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final LibraryUserRepository repository;

    public CustomUserDetailsService(
            LibraryUserRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        LibraryUser libraryUser = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(libraryUser.getEmail())
                .password(libraryUser.getPassword())
                .roles(libraryUser.getRole().name())
                .build();
    }
}
