package ru.shevchenko.subtracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.shevchenko.subtracker.controller.api.UserApi;
import ru.shevchenko.subtracker.dto.user.UserCreateDto;
import ru.shevchenko.subtracker.dto.user.UserResponseDto;
import ru.shevchenko.subtracker.dto.user.UserUpdateDto;
import ru.shevchenko.subtracker.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public UserResponseDto createUser(UserCreateDto dto) {
        return userService.createUser(dto);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateDto dto) {
        return userService.updateUser(id, dto);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
