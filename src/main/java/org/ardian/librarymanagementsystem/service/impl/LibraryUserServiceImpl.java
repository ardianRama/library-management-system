package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.ardian.librarymanagementsystem.service.LibraryUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {

    private final LibraryUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LibraryUserServiceImpl(
            LibraryUserRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(LibraryUserDto dto) {

        //TODO add new exception
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        //TODO create mapper?
        LibraryUser user = LibraryUser.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(Role.USER)
                .build();

        repository.save(user);
    }
}
