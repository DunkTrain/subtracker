package ru.shevchenko.subtracker.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Структура тела ошибки")
public record ErrorResponse(
        @Schema(description = "Сообщение об ошибке", example = "Пользователь с таким email уже существует")
        String error
) {}
