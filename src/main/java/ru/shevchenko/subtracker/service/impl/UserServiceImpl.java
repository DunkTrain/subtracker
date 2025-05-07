package ru.shevchenko.subtracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shevchenko.subtracker.audit.annotation.Auditable;
import ru.shevchenko.subtracker.dto.user.UserCreateDto;
import ru.shevchenko.subtracker.dto.user.UserResponseDto;
import ru.shevchenko.subtracker.dto.user.UserUpdateDto;
import ru.shevchenko.subtracker.exception.UserNotFoundException;
import ru.shevchenko.subtracker.mapper.UserMapper;
import ru.shevchenko.subtracker.model.Users;
import ru.shevchenko.subtracker.repository.UserRepository;
import ru.shevchenko.subtracker.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    @Auditable(action = "USER_CREATE")
    public UserResponseDto createUser(UserCreateDto dto) {
        Users user = userMapper.toEntity(dto);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    @Auditable(action = "USER_UPDATE")
    public UserResponseDto updateUser(Long id, UserUpdateDto dto) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        Users updated = userMapper.toEntity(dto, user);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    @Auditable(action = "USER_DELETE")
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }
}
