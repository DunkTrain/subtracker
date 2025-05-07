package ru.shevchenko.subtracker.mapper;

import org.springframework.stereotype.Component;
import ru.shevchenko.subtracker.dto.user.UserCreateDto;
import ru.shevchenko.subtracker.dto.user.UserResponseDto;
import ru.shevchenko.subtracker.dto.user.UserUpdateDto;
import ru.shevchenko.subtracker.model.Users;

/**
 * Маппер для преобразования между DTO и сущностями Users.
 */
@Component
public class UserMapper {

    /**
     * Преобразует DTO для создания пользователя в сущность Users.
     *
     * @param dto входной объект UserCreateDto
     * @return объект Users для сохранения в БД
     */
    public Users toEntity(UserCreateDto dto) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    /**
     * Обновляет существующую сущность пользователя на основе UserUpdateDto.
     *
     * @param dto входной объект UserUpdateDto
     * @param existing пользователь, который уже существует в базе
     * @return обновленный объект Users
     */
    public Users toEntity(UserUpdateDto dto, Users existing) {
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        return existing;
    }

    /**
     * Преобразует сущность Users в DTO ответа.
     *
     * @param entity объект Users
     * @return объект UserResponseDto для возврата через API
     */
    public UserResponseDto toDto(Users entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }
}
