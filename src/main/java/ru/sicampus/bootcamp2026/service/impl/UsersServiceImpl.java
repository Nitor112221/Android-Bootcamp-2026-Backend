package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exceptions.EmailAlreadyUsedException;
import ru.sicampus.bootcamp2026.exceptions.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.UsersService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;


    @Override
    public List<UserDTO> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(UserMapper::convertToDto)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return usersRepository.findById(id).map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        Optional<Users> optionalUser = usersRepository.findByEmail(dto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyUsedException("User with this email already exists");
        }

        Users user = new Users();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setPatronymic(dto.getPatronymic());
        user.setEmail(dto.getEmail());
        user.setPosition(dto.getPosition());
        user.setCreatedAt(dto.getCreatedAt());
        return UserMapper.convertToDto(usersRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not exist"));

        if (!user.getEmail().equals(dto.getEmail()) && usersRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("Your new email already use another user");
        }

        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setPatronymic(dto.getPatronymic());
        user.setEmail(dto.getEmail());
        user.setPosition(dto.getPosition());
        user.setCreatedAt(dto.getCreatedAt());
        return UserMapper.convertToDto(usersRepository.save(user));
    }

    @Override
    public void deletePerson(Long id) {
        if(usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not exists");
        }

        usersRepository.deleteById(id);
    }
}
