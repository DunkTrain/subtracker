package ru.shevchenko.subtracker.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionStatsDto;
import ru.shevchenko.subtracker.model.Subscriptions;
import ru.shevchenko.subtracker.model.Users;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
class SubscriptionRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.2-alpine")
            .withDatabaseName("subtracker_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanUp() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Проверка existsByUserIdAndServiceName работает корректно")
    void existsByUserIdAndServiceName_shouldReturnTrue() {
        Users user = userRepository.save(new Users(null, "Test", "test@mail.ru", null));
        Subscriptions sub = new Subscriptions(null, "Netflix", user);
        subscriptionRepository.save(sub);

        boolean exists = subscriptionRepository.existsByUserIdAndServiceName(user.getId(), "Netflix");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("findByUserId возвращает список подписок")
    void findByUserId_shouldReturnList() {
        Users user = userRepository.save(new Users(null, "User", "user@mail.ru", null));
        subscriptionRepository.save(new Subscriptions(null, "Netflix", user));
        subscriptionRepository.save(new Subscriptions(null, "YouTube", user));

        List<Subscriptions> result = subscriptionRepository.findByUserId(user.getId());
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("findTopPopular возвращает агрегированные данные")
    void findTopPopular_shouldReturnStats() {
        Users u1 = userRepository.save(new Users(null, "A", "a@mail.ru", null));
        Users u2 = userRepository.save(new Users(null, "B", "b@mail.ru", null));

        subscriptionRepository.save(new Subscriptions(null, "Netflix", u1));
        subscriptionRepository.save(new Subscriptions(null, "Netflix", u2));
        subscriptionRepository.save(new Subscriptions(null, "YouTube", u1));

        List<SubscriptionStatsDto> result = subscriptionRepository.findTopPopular(PageRequest.of(0, 3));

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result).isNotEmpty();
        softly.assertThat(result.get(0).getServiceName()).isEqualTo("Netflix");
        softly.assertThat(result.get(0).getCount()).isEqualTo(2);
        softly.assertAll();
    }
}
