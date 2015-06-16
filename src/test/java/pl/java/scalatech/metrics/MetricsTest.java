package pl.java.scalatech.metrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.Metrics2Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Metrics2Config.class })
public class MetricsTest {
    private SomeInstrumentedClass s = new SomeInstrumentedClass();
  
    
    @Test
    @Repeat(value=5)
    public void testTwo() throws InterruptedException{
        s.boom();
        s.takeSomeTime();
        s.tick();
        s.tock();
    }
    
    @Test
    public void waitTest() throws InterruptedException{
        Thread.sleep(1000*60*2);
    }
}
