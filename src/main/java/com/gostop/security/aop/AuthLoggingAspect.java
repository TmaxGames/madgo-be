package com.gostop.security.aop;

import com.gostop.security.dto.response.SessionLoginResponseDto;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
@Slf4j
@Aspect
public class AuthLoggingAspect {
    @Pointcut("execution(* com.gostop.security.controller..*.*(..))")
    private void cut() {}

    @Before("cut()")
    private void whenRequestArrived(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String controllerName = joinPoint.getSignature().getDeclaringType().getName();
        String sessionId = request.getSession().getId();

        String lineSep = System.lineSeparator();
        StringBuilder logSb = new StringBuilder(lineSep);
        logSb.append("============= REQUEST START ===========").append(lineSep);
        logSb.append("Request Url    : ").append(request.getRequestURL()).append(lineSep);
        logSb.append("Request Method : ").append(request.getMethod()).append(lineSep);
        logSb.append("Session Id     : ").append(sessionId).append(lineSep);
        logSb.append("Controller     : ").append(controllerName).append(lineSep);
        logSb.append("============= REQUEST END ===========").append(lineSep);
        log.info(logSb.toString());
    }

    @AfterReturning(pointcut = "cut()", returning = "responseObj")
    private void afterReturns(JoinPoint joinPoint, Objects responseObj) {
        String controllerName = joinPoint.getSignature().getDeclaringType().getName();
        String lineSep = System.lineSeparator();
        StringBuilder logSb = new StringBuilder(lineSep);
        logSb.append("============= Succeed =============").append(lineSep);
        logSb.append("Controller     : ").append(controllerName).append(lineSep);
        logSb.append("contents  : ").append(responseObj.toString()).append(lineSep);
        logSb.append("=========================================").append(lineSep);
        log.info(logSb.toString());
    }

    @AfterThrowing(pointcut = "cut()", throwing = "exception")
    private void afterThrows(JoinPoint joinPoint, Exception exception) {
        String controllerName = joinPoint.getSignature().getDeclaringType().getName();
        String lineSep = System.lineSeparator();
        StringBuilder logSb = new StringBuilder(lineSep);
        logSb.append("============= Failed =============").append(lineSep);
        logSb.append("Controller     : ").append(controllerName).append(lineSep);
        logSb.append("Exception  : ").append(exception.getMessage()).append(lineSep);
        logSb.append("=========================================").append(lineSep);
        log.info(logSb.toString());
    }
}
