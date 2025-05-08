package ru.shevchenko.subtracker.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubscriptionServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @BeforeEach
    void setUp() {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Добавление подписки — успешный сценарий")
    void addSubscription_shouldSucceed() {
        SubscriptionCreateDto dto = new SubscriptionCreateDto("Netflix");
        Users user = new Users(); user.setId(1L);
        Subscriptions sub = new Subscriptions(null, "Netflix", user);
        SubscriptionResponseDto expected = new SubscriptionResponseDto(1L, "Netflix");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(subscriptionRepository.existsByUserIdAndServiceName(1L, "Netflix")).thenReturn(false);
        when(subscriptionMapper.toEntity(dto)).thenReturn(sub);
        when(subscriptionRepository.save(sub)).thenReturn(sub);
        when(subscriptionMapper.toDto(sub)).thenReturn(expected);

        SubscriptionResponseDto result = subscriptionService.addSubscription(1L, dto);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Добавление подписки — пользователь не найден")
    void addSubscription_userNotFound() {
        SubscriptionCreateDto dto = new SubscriptionCreateDto("Netflix");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> subscriptionService.addSubscription(1L, dto));
    }

    @Test
    @DisplayName("Добавление подписки — уже существует")
    void addSubscription_alreadyExists() {
        SubscriptionCreateDto dto = new SubscriptionCreateDto("Netflix");
        Users user = new Users(); user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(subscriptionRepository.existsByUserIdAndServiceName(1L, "Netflix")).thenReturn(true);

        assertThrows(SubscriptionAlreadyExistsException.class, () -> subscriptionService.addSubscription(1L, dto));
    }

    @Test
    @DisplayName("Получение подписок по пользователю — успешный сценарий")
    void getSubscriptionsByUser_shouldReturnList() {
        Users user = new Users(); user.setId(1L);
        Subscriptions s = new Subscriptions(1L, "Netflix", user);
        SubscriptionResponseDto dto = new SubscriptionResponseDto(1L, "Netflix");

        when(userRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.findByUserId(1L)).thenReturn(List.of(s));
        when(subscriptionMapper.toDto(s)).thenReturn(dto);

        List<SubscriptionResponseDto> result = subscriptionService.getSubscriptionsByUser(1L);

        assertEquals(1, result.size());
        assertEquals("Netflix", result.get(0).getServiceName());
    }

    @Test
    @DisplayName("Получение подписок — пользователь не найден")
    void getSubscriptionsByUser_userNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> subscriptionService.getSubscriptionsByUser(1L));
    }

    @Test
    @DisplayName("Удаление подписки — успешный сценарий")
    void deleteSubscription_shouldDelete() {
        Users user = new Users(); user.setId(1L);
        Subscriptions s = new Subscriptions(2L, "VK", user);

        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(s));

        subscriptionService.deleteSubscription(1L, 2L);

        verify(subscriptionRepository).deleteById(2L);
    }

    @Test
    @DisplayName("Удаление подписки — не найдена")
    void deleteSubscription_notFound() {
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.deleteSubscription(1L, 2L));
    }

    @Test
    @DisplayName("Удаление подписки — чужая подписка")
    void deleteSubscription_notBelongToUser() {
        Users other = new Users(); other.setId(99L);
        Subscriptions s = new Subscriptions(2L, "VK", other);

        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(s));

        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.deleteSubscription(1L, 2L));
    }

    @Test
    @DisplayName("Получение топ-3 подписок")
    void getTopSubscriptions_shouldReturnList() {
        List<SubscriptionStatsDto> mockResult = List.of(
                new SubscriptionStatsDto("Netflix", 5L),
                new SubscriptionStatsDto("YouTube", 3L)
        );

        when(subscriptionRepository.findTopPopular(any())).thenReturn(mockResult);

        List<SubscriptionStatsDto> result = subscriptionService.getTopSubscriptions();

        assertEquals(2, result.size());
        assertEquals("Netflix", result.get(0).getServiceName());
    }
}
