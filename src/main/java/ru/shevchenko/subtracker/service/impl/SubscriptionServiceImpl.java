package ru.shevchenko.subtracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shevchenko.subtracker.audit.annotation.Auditable;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionCreateDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionResponseDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionStatsDto;
import ru.shevchenko.subtracker.exception.SubscriptionAlreadyExistsException;
import ru.shevchenko.subtracker.exception.SubscriptionNotFoundException;
import ru.shevchenko.subtracker.exception.UserNotFoundException;
import ru.shevchenko.subtracker.mapper.SubscriptionMapper;
import ru.shevchenko.subtracker.model.Subscriptions;
import ru.shevchenko.subtracker.model.Users;
import ru.shevchenko.subtracker.repository.SubscriptionRepository;
import ru.shevchenko.subtracker.repository.UserRepository;
import ru.shevchenko.subtracker.service.SubscriptionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    @Auditable(action = "SUBSCRIPTION_ADD")
    public SubscriptionResponseDto addSubscription(Long userId, SubscriptionCreateDto dto) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (subscriptionRepository.existsByUserIdAndServiceName(userId, dto.getServiceName())) {
            throw new SubscriptionAlreadyExistsException(dto.getServiceName());
        }

        Subscriptions subscription = subscriptionMapper.toEntity(dto);
        subscription.setUser(user);

        return subscriptionMapper.toDto(subscriptionRepository.save(subscription));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseDto> getSubscriptionsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscriptionMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    @Auditable(action = "SUBSCRIPTION_DELETE")
    public void deleteSubscription(Long userId, Long subscriptionId) {
        Subscriptions sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        if (!sub.getUser().getId().equals(userId)) {
            throw new SubscriptionNotFoundException(subscriptionId);
        }

        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionStatsDto> getTopSubscriptions() {
        return subscriptionRepository.findTopPopular(PageRequest.of(0, 3));
    }
}
