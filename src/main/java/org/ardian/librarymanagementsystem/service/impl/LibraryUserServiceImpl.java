package org.ardian.librarymanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.exception.business.ConflictException.UserAlreadyExistsException;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.mapper.internal.LibraryUserMapper;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.ardian.librarymanagementsystem.service.LibraryUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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

        if (repository.existsByEmail(dto.getEmail())) {

            log.warn("Attempt to register user with existing email: {}", dto.getEmail());

            throw new UserAlreadyExistsException(dto.getEmail());
        }

        LibraryUser user = LibraryUserMapper.libraryUserDtoToLibraryUserEntity(dto, Role.USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        LibraryUser saved = repository.save(user);

        log.info("User registered successfully. userId={}, email={}",
                saved.getId(),
                saved.getEmail());
    }
}
