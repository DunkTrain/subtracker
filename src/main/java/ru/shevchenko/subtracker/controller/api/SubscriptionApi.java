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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.shevchenko.subtracker.dto.error.ErrorResponse;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionCreateDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionResponseDto;
import ru.shevchenko.subtracker.dto.subscription.SubscriptionStatsDto;

import java.util.List;

@Tag(name = "Subscription API", description = "API для управления подписками пользователей")
@RequestMapping("/api")
public interface SubscriptionApi {

    @PostMapping("/users/{id}/subscriptions")
    @Operation(summary = "Добавить подписку пользователю")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Подписка успешно добавлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"serviceName: не должно быть пустым\"}"))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Подписка уже существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    SubscriptionResponseDto addSubscription(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionCreateDto dto);

    @GetMapping("/users/{id}/subscriptions")
    @Operation(summary = "Получить подписки пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список подписок пользователя"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    List<SubscriptionResponseDto> getSubscriptionsByUser(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable Long id);

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    @Operation(summary = "Удалить подписку у пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Подписка удалена"),
            @ApiResponse(responseCode = "404", description = "Подписка не найдена или не принадлежит пользователю",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSubscription(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable Long id,
            @Parameter(description = "ID подписки", required = true)
            @PathVariable Long subId);

    @GetMapping("/subscriptions/top")
    @Operation(summary = "Получить топ-3 популярных подписок")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список популярных подписок")
    })
    List<SubscriptionStatsDto> getTopSubscriptions();
}
