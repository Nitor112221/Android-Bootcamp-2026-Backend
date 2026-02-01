package ru.sicampus.bootcamp2026.service.impl;

import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.service.UsersService;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        return null;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        return null;
    }

    @Override
    public void deletePerson(Long id) {

    }
}
