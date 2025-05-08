package ru.shevchenko.subtracker.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.shevchenko.subtracker.dto.user.UserCreateDto;
import ru.shevchenko.subtracker.dto.user.UserResponseDto;
import ru.shevchenko.subtracker.dto.user.UserUpdateDto;
import ru.shevchenko.subtracker.exception.EmailAlreadyExistsException;
import ru.shevchenko.subtracker.exception.UserNotFoundException;
import ru.shevchenko.subtracker.mapper.UserMapper;
import ru.shevchenko.subtracker.model.Users;
import ru.shevchenko.subtracker.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Создание пользователя — успешный сценарий")
    void createUser_shouldCreateSuccessfully() {
        UserCreateDto dto = new UserCreateDto("Ivan", "ivan@mail.ru");
        Users user = new Users(1L, "Ivan", "ivan@mail.ru", null);
        UserResponseDto expected = new UserResponseDto(1L, "Ivan", "ivan@mail.ru");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expected);

        UserResponseDto result = userService.createUser(dto);

        assertEquals(expected, result);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Создание пользователя — email уже существует")
    void createUser_shouldThrowIfEmailExists() {
        UserCreateDto dto = new UserCreateDto("Ivan", "ivan@mail.ru");
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    @DisplayName("Получение пользователя по ID — успешный сценарий")
    void getUserById_shouldReturnUser() {
        Users user = new Users(1L, "Ivan", "ivan@mail.ru", null);
        UserResponseDto expected = new UserResponseDto(1L, "Ivan", "ivan@mail.ru");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(expected);

        UserResponseDto result = userService.getUserById(1L);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Получение пользователя — не найден")
    void getUserById_shouldThrowIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    @DisplayName("Обновление пользователя — успешный сценарий")
    void updateUser_shouldUpdateSuccessfully() {
        UserUpdateDto dto = new UserUpdateDto("Petr", "petr@mail.ru");
        Users user = new Users(1L, "Ivan", "ivan@mail.ru", null);
        Users updated = new Users(1L, "Petr", "petr@mail.ru", null);
        UserResponseDto expected = new UserResponseDto(1L, "Petr", "petr@mail.ru");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userMapper.toEntity(dto, user)).thenReturn(updated);
        when(userRepository.save(user)).thenReturn(updated);
        when(userMapper.toDto(updated)).thenReturn(expected);

        UserResponseDto result = userService.updateUser(1L, dto);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Обновление пользователя — не найден")
    void updateUser_shouldThrowIfUserNotFound() {
        UserUpdateDto dto = new UserUpdateDto("Petr", "petr@mail.ru");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, dto));
    }

    @Test
    @DisplayName("Обновление пользователя — email занят")
    void updateUser_shouldThrowIfEmailExists() {
        UserUpdateDto dto = new UserUpdateDto("Petr", "used@mail.ru");
        Users user = new Users(1L, "Ivan", "ivan@mail.ru", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.updateUser(1L, dto));
    }

    @Test
    @DisplayName("Удаление пользователя — успешный сценарий")
    void deleteUser_shouldDeleteSuccessfully() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Удаление пользователя — не найден")
    void deleteUser_shouldThrowIfNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
