package ru.shevchenko.subtracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.shevchenko.subtracker.dto.error.ErrorResponse;
import ru.shevchenko.subtracker.dto.user.UserCreateDto;
import ru.shevchenko.subtracker.dto.user.UserResponseDto;
import ru.shevchenko.subtracker.dto.user.UserUpdateDto;

@Tag(name = "User API", description = "API для управления пользователями")
@RequestMapping("/api/users")
public interface UserApi {

    /* ---------- CREATE ---------- */
    @PostMapping
    @Operation(summary = "Создать пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(
                    responseCode = "400", description = "Ошибка валидации запроса",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"email: должно быть корректным адресом\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "409", description = "Email уже занят",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Пользователь с таким email уже существует\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Внутренняя ошибка сервера\"}")
                    )
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    UserResponseDto createUser(@RequestBody @Valid UserCreateDto dto);

    /* ---------- READ ---------- */
    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(
                    responseCode = "404", description = "Пользователь не найден",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Пользователь с id 42 не найден\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Внутренняя ошибка сервера\"}")
                    )
            )
    })
    UserResponseDto getUserById(@Parameter(description = "ID пользователя", required = true)
                                @PathVariable Long id);

    /* ---------- UPDATE ---------- */
    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь обновлён"),
            @ApiResponse(
                    responseCode = "400", description = "Ошибка валидации запроса",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"name: не должно быть пустым\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Пользователь не найден",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Пользователь с id 42 не найден\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "409", description = "Email уже используется другим пользователем",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Пользователь с таким email уже существует\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Внутренняя ошибка сервера\"}")
                    )
            )
    })
    UserResponseDto updateUser(@Parameter(description = "ID пользователя", required = true)
                               @PathVariable Long id,
                               @RequestBody @Valid UserUpdateDto dto);

    /* ---------- DELETE ---------- */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
            @ApiResponse(
                    responseCode = "404", description = "Пользователь не найден",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Пользователь с id 42 не найден\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Внутренняя ошибка сервера\"}")
                    )
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@Parameter(description = "ID пользователя", required = true)
                    @PathVariable Long id);
}
