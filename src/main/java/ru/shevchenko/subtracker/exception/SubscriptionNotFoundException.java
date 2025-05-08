package ru.shevchenko.subtracker.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(Long subId) {
        super("Подписка с id " + subId + " не найдена или не принадлежит пользователю");
    }
}
