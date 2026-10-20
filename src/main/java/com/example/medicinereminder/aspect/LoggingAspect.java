
package com.example.medicinereminder.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.medicinereminder.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint)
            throws Throwable {

        String methodName =
                joinPoint.getSignature().toShortString();

        logger.info("Starting method: {}", methodName);

        try {
            Object result = joinPoint.proceed();
            logger.info("Completed method: {}", methodName);
            return result;
        } catch (Throwable exception) {
            logger.error("Method failed: {}", methodName, exception);
            throw exception;
        }
    }
}
