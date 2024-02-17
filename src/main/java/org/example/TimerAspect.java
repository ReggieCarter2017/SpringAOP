package org.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.example.annotations.RecoverException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class TimerAspect {

    @Pointcut("within(@org.example.annotations.Timer *)")
    public void timerPointcut() {
    }


    @Around("timerPointcut()")
    public Object execTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            System.out.println(joinPoint.getSignature().getDeclaringTypeName() + ", " +
                    joinPoint.getSignature().getName() + " #(" + (System.nanoTime() - start) + ")");
        }
    }

    @Around("@annotation(RecoverException)")
    public Object exceptionHandle(ProceedingJoinPoint joinPoint, RecoverException recoverException) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            Class<? extends RuntimeException>[] noRecoverFor = recoverException.noRecoverFor();
            String methodName = joinPoint.getSignature().getName();
            if (noRecoverFor != null && noRecoverFor.length > 0) {
                for (Class<? extends RuntimeException> exceptionClass : noRecoverFor) {
                    if (exceptionClass.isInstance(ex)) {
                        throw ex;
                    }
                    else {
                        System.out.println("Exception handled");
                        return null;
                    }
                }
            }
            return null;
        }
    }

}
