package ru.shevchenko.subtracker.audit.annotation;

import ru.shevchenko.subtracker.audit.AuditAspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для аудита бизнес-действий.
 * <p>
 * Используется совместно с {@link AuditAspect} для логирования вызовов
 * методов сервисного слоя, которые представляют важные бизнес-операции.
 * </p>
 *
 * <p>По умолчанию аудит логирует:</p>
 * <ul>
 *   <li>Тип действия (action)</li>
 *   <li>Имя класса и метода</li>
 *   <li>Аргументы метода</li>
 * </ul>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

    /**
     * Название действия для логирования.
     *
     * @return строковый код действия
     */
    String action();
}
