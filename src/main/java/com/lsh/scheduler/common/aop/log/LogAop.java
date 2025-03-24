package com.lsh.scheduler.common.aop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@Aspect
@Component
public class LogAop {
    @Pointcut("execution(* com.lsh.scheduler.module..controller.*Controller.*(..))")
    private void logPointcut() {

    }

    @Before("logPointcut()")
    public void beforeParameterLog(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        log.info("메서드 실행 전");
        getMethodInfo(method);

        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            log.info("no parameters");
        } else {
            getParameterInfo(method, args);
        }
    }

    @AfterReturning(value = "logPointcut()", returning = "returnObj")
    public void afterReturningLog(JoinPoint joinPoint, Object returnObj) {
        Method method = getMethod(joinPoint);
        log.info("메서드 실행 후");
        getMethodInfo(method);
        log.info("return type = {}", returnObj.getClass().getSimpleName());
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    private void getParameterInfo(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            String parameterName = parameters[i].getName();
            Object arg = (args != null && i < args.length) ? args[i] : null;

            if (arg == null) {
                log.info("parameter {} is null", parameterName);
            } else {
                log.info("parameter {} {}", arg.getClass().getSimpleName(), parameterName);
            }
        }
    }

    private void getMethodInfo(Method method) {
        log.info("method name = {}", method.getName());
    }

}
