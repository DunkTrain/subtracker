package ru.shevchenko.subtracker.service;

import ru.shevchenko.subtracker.dto.user.UserCreateDto;
import ru.shevchenko.subtracker.dto.user.UserResponseDto;
import ru.shevchenko.subtracker.dto.user.UserUpdateDto;

/**
 * Интерфейс для работы с пользователем.
 * Определяет базовые CRUD-операции.
 */
public interface UserService {

    /**
     * Создает нового пользователя.
     *
     * @param dto данные для создания пользователя
     * @return созданный пользователь
     */
    UserResponseDto createUser(UserCreateDto dto);

    /**
     * Возвращает пользователя по ID.
     *
     * @param id пользователя
     * @return найденный пользователь
     */
    UserResponseDto getUserById(Long id);

    /**
     * Обновляет данные пользователя.
     *
     * @param id идентификатор пользователя
     * @param dto новые данные пользователя
     * @return обновленный пользователь
     */
    UserResponseDto updateUser(Long id, UserUpdateDto dto);

    /**
     * Удаляет пользователя по ID.
     *
     * @param id пользователя
     */
    void deleteUser(Long id);
}
