package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.LibraryUserDetailedDto;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;

import java.util.List;

public interface LibraryUserService {

    void registerUser(LibraryUserDto dto);

    List<LibraryUserDetailedDto> getUsers();
}
