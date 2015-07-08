package pl.java.scalatech;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import pl.java.scalatech.config.AppConfig;
import pl.java.scalatech.domain.Dictionary;
import pl.java.scalatech.service.DictionaryService;

@Slf4j
@Configuration
@Import(AppConfig.class)
public class MetricsCodaForServer extends SpringBootServletInitializer {
    private static final String DEV = "dev";

    @Autowired
    private DictionaryService dictionaryService;

    public static void main(String[] args) {
        SpringApplication.run(MetricsCodaForServer.class, args);
    }

    public static void configure() {
        log.info("+++ configure servlet");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        SpringApplicationBuilder app = application.profiles(addDefaultProfile()).sources(WebConfiguration.class);
        // application.initializers(new Initializer());
        return app;
    }

    @Configuration
    @Import(AppConfig.class)
    @ComponentScan(excludeFilters = @Filter({ Service.class, Configuration.class }))
    static class WebConfiguration {

    }

    private static String addDefaultProfile() {
        String profile = System.getProperty("spring.profiles.default");
        if (profile != null) {
            log.info("+++                                     Running with Spring profile(s) : {}", profile);
            return profile;
        }
        log.warn("+++                                    No Spring profile configured, running with default configuration");
        return DEV;
    }

    @Bean
    InitializingBean populateData(final DictionaryService dictionaryService) {
        return () -> {

            dictionaryService.save(Dictionary.builder().word("ok").translateWord("w porzadku").build());
            dictionaryService.save(Dictionary.builder().word("hesitate").translateWord("wahac sie").build());
            dictionaryService.save(Dictionary.builder().word("all").translateWord("wszystko").build());
            dictionaryService.save(dictionaryService.save(Dictionary.builder().word("master").translateWord("pan").build()));
            dictionaryService.save(dictionaryService.save(Dictionary.builder().word("slave").translateWord("niewolnik").build()));
            dictionaryService.save(dictionaryService.save(Dictionary.builder().word("yes").translateWord("tak").build()));
            dictionaryService.save(dictionaryService.save(Dictionary.builder().word("no").translateWord("nie").build()));
        };
    }
}
