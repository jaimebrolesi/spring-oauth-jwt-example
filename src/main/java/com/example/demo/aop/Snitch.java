package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Aspect
@Component
public class Snitch {

    private static final Logger LOGGER = LoggerFactory.getLogger(Snitch.class);

    @Around("execution(* com.example.demo..*.*(..)) && !execution(*.new(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable t) {
            final String className = joinPoint.getSignature().getDeclaringTypeName();
            final String methodName = joinPoint.getSignature().getName();
            final Class[] argType = Stream.of(joinPoint.getArgs())
                    .map(Object::getClass)
                    .toArray(Class[]::new);
            final Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, argType);
            final Parameter[] parameters = method.getParameters();
            final Object[] args = joinPoint.getArgs();
            final List<String> collect = IntStream.range(0, joinPoint.getArgs().length)
                    .mapToObj(i -> parameters[i].getName().concat("=" + args[i]))
                    .collect(Collectors.toList());

            LOGGER.error("{}.{}({})", className, methodName, collect);
            throw t;
        }
    }
}
