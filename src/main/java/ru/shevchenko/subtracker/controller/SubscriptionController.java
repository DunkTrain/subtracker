package ru.shevchenko.subtracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.shevchenko.subtracker.controller.api.SubscriptionApi;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionCreateDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionResponseDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionStatsDto;
import ru.shevchenko.subtracker.service.SubscriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionApi {

    private final SubscriptionService subscriptionService;

    @Override
    public SubscriptionResponseDto addSubscription(Long id, SubscriptionCreateDto dto) {
        return subscriptionService.addSubscription(id, dto);
    }

    @Override
    public List<SubscriptionResponseDto> getSubscriptionsByUser(Long id) {
        return subscriptionService.getSubscriptionsByUser(id);
    }

    @Override
    public void deleteSubscription(Long id, Long subId) {
        subscriptionService.deleteSubscription(id, subId);
    }

    @Override
    public List<SubscriptionStatsDto> getTopSubscriptions() {
        return subscriptionService.getTopSubscriptions();
    }
}
