package pl.java.scalatech.test.validation;

import javax.validation.ConstraintViolationException;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.config.ValidationConfig;
import pl.java.scalatech.service.DemoService;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MongoDBConfig.class, MongoRepositoryConfig.class, ValidationConfig.class, ServiceConfig.class
 /*LoggingAspectConfiguration.class*/ })
@ActiveProfiles(profiles = "dev,cache")
public class DemoServiceTest {
    @Autowired
    private DemoService demoService;
    @Test
    public void shouldValidate1() {
        Assertions.assertThat(demoService).isNotNull();
        demoService.doSomething("slawek", Lists.newArrayList("przodownik","3"));
    }
    @Test(expected = ConstraintViolationException.class)
    public void testBadMessage() {
    demoService.doSomething("", Lists.newArrayList("przodownik","t"));
    }
  
}
