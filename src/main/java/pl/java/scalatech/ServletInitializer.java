package pl.java.scalatech;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

// Create a deployable war file
@Slf4j
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        log.info("+++                                    servletInitializer");
        MetricsCodaForServer.configure();
        return application.sources(MetricsCodaForServer.class);
    }
}