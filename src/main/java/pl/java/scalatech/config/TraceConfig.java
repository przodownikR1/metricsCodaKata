package pl.java.scalatech.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class TraceConfig {
    @Bean
    public CustomizableTraceInterceptor interceptor() {
        CustomizableTraceInterceptor interceptor = new CustomizableTraceInterceptor();
        interceptor.setEnterMessage("Entering $[methodName]($[arguments]).");
        interceptor.setExitMessage("Leaving $[methodName](..) with return value $[returnValue], took $[invocationTime]ms.");
        return interceptor;
    }

    @Bean
    public Advisor traceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * org.springframework.data.repository.Repository+.*(..))");
        return new DefaultPointcutAdvisor(pointcut, interceptor());
    }
}
