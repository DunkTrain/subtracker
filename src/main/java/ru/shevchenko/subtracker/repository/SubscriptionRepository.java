package ru.shevchenko.subtracker.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionStatsDto;
import ru.shevchenko.subtracker.model.Subscriptions;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Subscriptions}.
 * Позволяет выполнять операции поиска и статистики по подпискам.
 */
public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {

    /**
     * Получить список всех подписок пользователя.
     *
     * @param userId ID пользователя
     * @return список подписок
     */
    List<Subscriptions> findByUserId(Long userId);

    /**
     * Проверяет, существует ли подписка на сервис у данного пользователя.
     *
     * @param userId ID пользователя
     * @param serviceName название сервиса
     * @return true — если подписка уже существует
     */
    boolean existsByUserIdAndServiceName(Long userId, String serviceName);

    /**
     * Получить топ популярных подписок (по количеству пользователей).
     *
     * @param pageable пагинация (например, top 3)
     * @return список DTO со статистикой подписок
     */
    @Query("SELECT new ru.shevchenko.subtracker.dto.subscription.SubscriptionStatsDto(s.serviceName, COUNT(s)) " +
            "FROM Subscriptions s GROUP BY s.serviceName ORDER BY COUNT(s) DESC")
    List<SubscriptionStatsDto> findTopPopular(Pageable pageable);
}
