package ru.shevchenko.subtracker.mapper;

import org.springframework.stereotype.Component;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionCreateDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionResponseDto;
import ru.shevchenko.subtracker.model.Subscriptions;

/**
 * Маппер для преобразования между DTO и сущностью Subscriptions.
 */
@Component
public class SubscriptionMapper {

    /**
     * Преобразует DTO для создания подписки в сущность Subscriptions.
     *
     * @param dto входной объект SubscriptionCreateDto
     * @return новая сущность подписки
     */
    public Subscriptions toEntity(SubscriptionCreateDto dto) {
        Subscriptions subscription = new Subscriptions();
        subscription.setServiceName(dto.getServiceName());
        return subscription;
    }

    /**
     * Преобразует сущность подписки в DTO для ответа.
     *
     * @param entity объект подписки
     * @return DTO ответа
     */
    public SubscriptionResponseDto toDto(Subscriptions entity) {
        return new SubscriptionResponseDto(
                entity.getId(),
                entity.getServiceName()
        );
    }
}
