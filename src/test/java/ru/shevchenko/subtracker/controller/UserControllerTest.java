package ru.shevchenko.subtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ru.shevchenko.subtracker.dto.user.UserCreateDto;
import ru.shevchenko.subtracker.dto.user.UserUpdateDto;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Создание пользователя — 201 Created")
    void createUser_success() throws Exception {
        UserCreateDto dto = new UserCreateDto("Тест", "test@mail.ru");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Тест")))
                .andExpect(jsonPath("$.email", is("test@mail.ru")));
    }

    @Test
    @DisplayName("Получение пользователя по ID — 200 OK")
    void getUserById_success() throws Exception {
        UserCreateDto dto = new UserCreateDto("Demo", "demo@mail.ru");
        String body = objectMapper.writeValueAsString(dto);

        ResultActions created = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));

        String location = created.andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(location).get("id").asLong();

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Demo")))
                .andExpect(jsonPath("$.email", is("demo@mail.ru")));
    }

    @Test
    @DisplayName("Обновление пользователя — 200 OK")
    void updateUser_success() throws Exception {
        UserCreateDto dto = new UserCreateDto("Alex", "alex@mail.ru");
        String body = objectMapper.writeValueAsString(dto);

        String content = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(content).get("id").asLong();

        UserUpdateDto update = new UserUpdateDto("Alexey", "alexey@mail.ru");

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alexey")))
                .andExpect(jsonPath("$.email", is("alexey@mail.ru")));
    }

    @Test
    @DisplayName("Удаление пользователя — 204 No Content")
    void deleteUser_success() throws Exception {
        UserCreateDto dto = new UserCreateDto("ToDelete", "to-delete@mail.ru");
        String body = objectMapper.writeValueAsString(dto);

        String content = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(content).get("id").asLong();

        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Создание пользователя с повторяющимся email — 409 Conflict")
    void createUser_duplicateEmail() throws Exception {
        UserCreateDto dto = new UserCreateDto("Dup", "dup@mail.ru");
        String body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error", containsString("уже существует")));
    }
}
