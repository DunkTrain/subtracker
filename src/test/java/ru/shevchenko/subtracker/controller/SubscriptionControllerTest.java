package ru.shevchenko.subtracker.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionCreateDto;
import ru.shevchenko.subtracker.dto.user.UserCreateDto;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long createUser(String name, String email) throws Exception {
        UserCreateDto dto = new UserCreateDto(name, email);
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(response);
        return json.get("id").asLong();
    }

    @Test
    @DisplayName("Добавление подписки — 201 Created")
    void addSubscription_success() throws Exception {
        Long userId = createUser("SubUser", "sub@mail.ru");
        SubscriptionCreateDto dto = new SubscriptionCreateDto("Netflix");

        mockMvc.perform(post("/api/users/{id}/subscriptions", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.serviceName", is("Netflix")));
    }

    @Test
    @DisplayName("Получение подписок — 200 OK")
    void getSubscriptions_success() throws Exception {
        Long userId = createUser("ListUser", "list@mail.ru");
        mockMvc.perform(post("/api/users/{id}/subscriptions", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new SubscriptionCreateDto("YouTube"))));

        mockMvc.perform(get("/api/users/{id}/subscriptions", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceName", is("YouTube")));
    }

    @Test
    @DisplayName("Удаление подписки — 204 No Content")
    void deleteSubscription_success() throws Exception {
        Long userId = createUser("DelUser", "del@mail.ru");
        String response = mockMvc.perform(post("/api/users/{id}/subscriptions", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SubscriptionCreateDto("VK Music"))))
                .andReturn().getResponse().getContentAsString();

        Long subId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/users/{id}/subscriptions/{subId}", userId, subId))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/users/{id}/subscriptions/{subId}", userId, subId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Получение топ-3 подписок — 200 OK")
    void getTopSubscriptions_success() throws Exception {
        Long id1 = createUser("Top1", "t1@mail.ru");
        Long id2 = createUser("Top2", "t2@mail.ru");
        Long id3 = createUser("Top3", "t3@mail.ru");

        mockMvc.perform(post("/api/users/{id}/subscriptions", id1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new SubscriptionCreateDto("Yandex PLUS"))));
        mockMvc.perform(post("/api/users/{id}/subscriptions", id2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new SubscriptionCreateDto("Yandex PLUS"))));
        mockMvc.perform(post("/api/users/{id}/subscriptions", id3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new SubscriptionCreateDto("Yandex PLUS"))));

        mockMvc.perform(get("/api/subscriptions/top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceName", is("Yandex PLUS")))
                .andExpect(jsonPath("$[0].count", greaterThanOrEqualTo(3)));
    }
}
