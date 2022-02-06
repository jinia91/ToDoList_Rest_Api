package jinia.todoapp.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 로그 추적을 위한 AOP 설정
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class LogAspect {

    private final LogTracer logTracer;

    @Around("execution(* jinia.todoapp..*Controller.*(..))||execution(* jinia.todoapp..*Service.*(..))||execution(* jinia.todoapp..*Repository.*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        boolean hasException = false;
        try {
            status = logTracer.begin(joinPoint.getSignature().toString());
            return joinPoint.proceed();
        } catch (Exception ex) {
            logTracer.exception(status, ex);
            hasException = true;
            throw ex;
        } finally {
            if(!hasException) logTracer.end(status);
        }
    }
}
