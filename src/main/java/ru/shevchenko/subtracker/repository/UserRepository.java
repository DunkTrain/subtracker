package ru.shevchenko.subtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shevchenko.subtracker.model.Users;

/**
 * Репозиторий для работы с сущностью {@link Users}
 * Обеспечивает доступ к пользовательским данным.
 */
public interface UserRepository extends JpaRepository<Users, Long> {}
