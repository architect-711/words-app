package edu.architect_711.words.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j @Component @Aspect
public class ExceptionLoggingAspect {
    private ProceedingJoinPoint joinPoint;

    @Around("@annotation(ExceptionLoggable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        this.joinPoint = joinPoint;

        basePrint();
        printRawException();

        return joinPoint.proceed();
    }

    private void basePrint() {
        String callingMethodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.error("called method {} with args {}", callingMethodName, args);
    }

    private void printRawException() {
        ((Throwable) joinPoint.getArgs()[0]).printStackTrace();
    }

}
