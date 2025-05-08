package ru.shevchenko.subtracker.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shevchenko.subtracker.dto.error.ErrorResponse;
import ru.shevchenko.subtracker.exception.EmailAlreadyExistsException;
import ru.shevchenko.subtracker.exception.UserNotFoundException;

/**
 * Обрабатывает предсказуемые ошибки и возвращает структурированные ответы клиенту.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает ситуацию, когда пользователь не найден.
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Обрабатывает ошибки валидации DTO (аннотация @Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Ошибка валидации данных");
        return new ErrorResponse(errorMessage);
    }

    /**
     * Обрабатывает ситуацию, когда email уже занят.
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailExists(EmailAlreadyExistsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Обрабатывает любые другие непредвиденные исключения.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherExceptions(Exception ex) {
        return new ErrorResponse("Внутренняя ошибка сервера");
    }
}
