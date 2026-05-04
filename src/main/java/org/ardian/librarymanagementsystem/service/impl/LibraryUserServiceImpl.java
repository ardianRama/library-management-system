package org.ardian.librarymanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.dto.LibraryUserDetailedDto;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.UserAlreadyExistsException;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.mapper.internal.LibraryUserMapper;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.ardian.librarymanagementsystem.service.LibraryUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LibraryUserServiceImpl implements LibraryUserService {

    private final LibraryUserRepository libraryUserRepository;
    private final PasswordEncoder passwordEncoder;

    public LibraryUserServiceImpl(
            LibraryUserRepository libraryUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.libraryUserRepository = libraryUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * for admin
     */

    @Override
    public List<LibraryUserDetailedDto> getUsers() {
        return libraryUserRepository.findAll()
                .stream()
                .map(LibraryUserMapper::libraryUserEntityToLibraryUserDetailedDto)
                .toList();
    }


    /**
     * for user
     */

    @Override
    public void registerUser(LibraryUserDto dto) {

        if (libraryUserRepository.existsByEmail(dto.getEmail())) {

            log.warn("Attempt to register user with existing email: {}", dto.getEmail());

            throw new UserAlreadyExistsException(dto.getEmail());
        }

        LibraryUser user = LibraryUserMapper.libraryUserDtoToLibraryUserEntity(dto, Role.USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        LibraryUser saved = libraryUserRepository.save(user);

        log.info("User registered successfully. userId={}, email={}",
                saved.getId(),
                saved.getEmail());
    }
}
