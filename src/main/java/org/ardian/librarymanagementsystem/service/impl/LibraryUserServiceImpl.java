package org.ardian.librarymanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.dto.LibraryUserDetailedDto;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.UserAlreadyExistsException;
import org.ardian.librarymanagementsystem.exception.business.conflict.UserHasActiveLoansException;
import org.ardian.librarymanagementsystem.exception.business.notfound.UserNotFoundException;
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

    @Override
    public void registerUser(LibraryUserDto dto) {

        if (libraryUserRepository.existsByEmail(dto.getEmail())) {
            log.info("Attempt to register user with existing email: {}", dto.getEmail());
            throw new UserAlreadyExistsException();
        }

        LibraryUser user = LibraryUserMapper.toEntity(dto, Role.USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        LibraryUser saved = libraryUserRepository.save(user);

        log.info("User registered successfully. userId={}, email={}",
                saved.getId(),
                saved.getEmail());
    }

    @Override
    public List<LibraryUserDetailedDto> getUsers() {
        return libraryUserRepository.findAll()
                .stream()
                .map(LibraryUserMapper::toDetailedDto)
                .toList();
    }

    @Override
    public LibraryUserDetailedDto getUserById(Long id) {
        return libraryUserRepository.findById(id)
                .map(LibraryUserMapper::toDetailedDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteLibraryUser(Long id) {

        LibraryUser user = libraryUserRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        boolean hasActiveLoans = user.getMyLoans()
                .stream()
                .anyMatch(loan -> loan.getReturnedAt() == null);

        if (hasActiveLoans) {
            log.warn("Attempt to delete user with active loans. user id={}", id);
            throw new UserHasActiveLoansException("User cannot be deleted while having active loans");
        }

        libraryUserRepository.delete(user);

        log.info("Successfully deleted user with id={}", id);
    }
}