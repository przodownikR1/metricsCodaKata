package pl.java.scalatech.web.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.Counter;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.ryantenney.metrics.annotation.Counted;

@RestController
@Slf4j
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public class HelloController {
 
    Random rand = new Random();
    
    @Autowired
    private Counter counter;

    @RequestMapping("/hello")
    public String hello() {
        return "hello ...";
    }
    @Timed(name="car")
    @RequestMapping("/car")
    public Car car() {
        return new Car("bmw", "10");
    }
    
    
    @RequestMapping("/inc")
    public String inc(){
       counter.inc();
       return "inc " + counter.getCount();
    }
    @RequestMapping("/dec")
    public String dec(){
       counter.dec();
       return "dec " + counter.getCount() ;
    }
    
    @Metered(name = "home view Meter", absolute=true)
    @Counted(name = "homeCount",monotonic=true)
    @Timed
    @RequestMapping("/car1")
    public Car car1() {
        return new Car("opel", "1");
    }
    
    @Metered(name = "home view Meter", absolute=true)
    @Counted(name = "homeCount",monotonic=true)
    @Timed
    @RequestMapping("/car2")
    public Car car2() {
        Counter count = new Counter();
        count.inc();
        return new Car("opel", "1");
    }
    
    @Timed
    @RequestMapping("/carMetrics")
    public Car carMetrics() throws InterruptedException {

        int r = rand.nextInt(1000);
        Thread.sleep(TimeUnit.MILLISECONDS.toMillis(new Long(r)));
        return new Car("bmw", "10");
    }

    @RequestMapping("/carException")
    @ExceptionMetered
    public Car carMetricsException() throws InterruptedException {
        throw new IllegalStateException("attempt error situation invoke...");
    }

    @Data
    @AllArgsConstructor
    public class Car {

        private String name;

        private String age;
    }
}
