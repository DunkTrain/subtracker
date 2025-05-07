package ru.shevchenko.subtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shevchenko.subtracker.model.Users;

/**
 * Репозиторий для работы с сущностью {@link Users}
 * Обеспечивает доступ к пользовательским данным.
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    /**
     * Проверяет, существует ли пользователь с указанным email.
     * Используется для обеспечения уникальности email при создании пользователя.
     *
     * @param email адрес электронной почты
     * @return true — если пользователь с таким email уже существует, иначе false
     */
    boolean existsByEmail(String email);
}
