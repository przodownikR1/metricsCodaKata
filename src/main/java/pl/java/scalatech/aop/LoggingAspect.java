package pl.java.scalatech.aop;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import pl.java.scalatech.interceptor.SignatureUtils;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Autowired
    private Environment env;

    @Autowired
    MetricRegistry registry;

    @Pointcut("within(pl.java.scalatech.repository..*) || within(pl.java.scalatech.service..*) || within(pl.java.scalatech.web..*)")
    public void loggingPoincut() {
    }

    @AfterThrowing(pointcut = "loggingPoincut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("^^^  Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                e.getCause(), e);
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

   /* @Around(value = "publicMethod() && execution(@com.codahale.metrics.annotation.Timed * *(..)) && @annotation(timedAnnotation)")
    public Object logAround(ProceedingJoinPoint joinPoint, Timed timedAnnotation) throws Throwable {
        final String signature = SignatureUtils.getSignature(joinPoint.getSignature(), timedAnnotation.name(), timedAnnotation.absolute());
        log.info("^^^-------------^^^ Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature()
                .getName(), Arrays.toString(joinPoint.getArgs()));

        return timeMethodCall(joinPoint, signature);
    }

    public Object timeMethodCall(ProceedingJoinPoint pjp, String signature) throws Throwable {
        final Timer.Context time = registry.timer(name("timer", signature)).time();
        try {
            return pjp.proceed();
        } finally {
            time.stop();
        }
    }*/
}
