package pl.java.scalatech.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectTwo {

@Before("execution (@org.springframework.web.bind.annotation.RequestMapping * *(..))")
public void logbefore(JoinPoint joinPoint){
System.out.println("logBefore() is running!");
System.out.println("!!!: " + joinPoint.getSignature().getName());

}
}