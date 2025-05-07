package ru.shevchenko.subtracker.audit;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.shevchenko.subtracker.audit.annotation.Auditable;

/**
 * Аспект, логирующий вызовы методов, помеченных {@link Auditable}.
 * <p>
 * Используется для централизованного бизнес-аудита действий.
 */
@Slf4j
@Aspect
@Component
public class AuditAspect {

    /**
     * Помечает метод как подлежащий аудиту.
     * Используется для логирования ключевых бизнес-действий.
     */
    @Around("@annotation(auditable)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        Object result = joinPoint.proceed();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String method = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        log.info("[AUDIT] Действие='{}', Метод='{}'", auditable.action(), method);

        return result;
    }
}
