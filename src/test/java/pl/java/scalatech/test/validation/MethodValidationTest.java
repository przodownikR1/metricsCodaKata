package pl.java.scalatech.test.validation;

import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.LoggingAspectConfiguration;
import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.config.ValidationConfig;
import pl.java.scalatech.domain.Dictionary;
import pl.java.scalatech.service.DictionaryService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MongoDBConfig.class, MongoRepositoryConfig.class, ValidationConfig.class, ServiceConfig.class,
 LoggingAspectConfiguration.class })
@ActiveProfiles(profiles = "dev,cache")
@Slf4j
public class MethodValidationTest {
    @Autowired
    private DictionaryService dictionaryService;

    @Test(expected = ConstraintViolationException.class)
    public void shouldValidate1() {
        Assertions.assertThat(dictionaryService).isNotNull();
        Dictionary test = dictionaryService.myValidMethod("slave");
        log.info("+++ {}", test);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldValidate2() {
        Assertions.assertThat(dictionaryService).isNotNull();
        dictionaryService.show(Dictionary.builder().word("as").translateWord("jako").build());

    }

}
