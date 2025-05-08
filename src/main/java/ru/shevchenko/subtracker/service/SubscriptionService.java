package ru.shevchenko.subtracker.service;

import ru.shevchenko.subtracker.dto.subscription.SubscriptionCreateDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionResponseDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionStatsDto;

import java.util.List;

/**
 * Сервис для управления подписками пользователей.
 */
public interface SubscriptionService {

    /**
     * Добавить новую подписку пользователю.
     *
     * @param userId ID пользователя
     * @param dto данные подписки
     * @return созданная подписка
     */
    SubscriptionResponseDto addSubscription(Long userId, SubscriptionCreateDto dto);

    /**
     * Получить список подписок пользователя.
     *
     * @param userId ID пользователя
     * @return список подписок
     */
    List<SubscriptionResponseDto> getSubscriptionsByUser(Long userId);

    /**
     * Удалить подписку пользователя по её ID.
     *
     * @param userId ID пользователя
     * @param subscriptionId ID подписки
     */
    void deleteSubscription(Long userId, Long subscriptionId);

    /**
     * Получить топ-3 самых популярных подписок.
     *
     * @return список статистик по сервисам
     */
    List<SubscriptionStatsDto> getTopSubscriptions();
}
