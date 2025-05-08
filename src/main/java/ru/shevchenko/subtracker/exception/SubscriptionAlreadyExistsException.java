package ru.shevchenko.subtracker.exception;

public class SubscriptionAlreadyExistsException extends RuntimeException {
    public SubscriptionAlreadyExistsException(String serviceName) {
        super("Подписка на сервис \"" + serviceName + "\" уже существует у пользователя");
    }
}
