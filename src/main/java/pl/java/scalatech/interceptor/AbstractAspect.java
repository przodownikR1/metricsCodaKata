package pl.java.scalatech.interceptor;

import org.aspectj.lang.annotation.Pointcut;

public abstract class AbstractAspect {
@Pointcut("execution(public * *(..))")
public void publicMethod() {}
}