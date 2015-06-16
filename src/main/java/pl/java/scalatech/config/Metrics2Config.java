package pl.java.scalatech.config;

import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.java.scalatech.config.metrics.BasicHealthCheck;
import pl.java.scalatech.config.metrics.DatabaseHealthCheck;
import pl.java.scalatech.config.metrics.DiskCapacityHealthCheck;
import pl.java.scalatech.config.metrics.RestResourcesHealthCheck;
import pl.java.scalatech.interceptor.LoggingMetricRegistryListener;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.mongodb.Mongo;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
@Slf4j
@org.springframework.context.annotation.Import(value=MongoDBConfig.class)
public class Metrics2Config extends MetricsConfigurerAdapter {

    private final MetricRegistry metricRegistry = new MetricRegistry();
    private final HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
    
    @Autowired
    private Mongo mongo;
    
    @Bean
    @Override
    public MetricRegistry getMetricRegistry() {
        metricRegistry.registerAll(new GarbageCollectorMetricSet());
        metricRegistry.registerAll(new MemoryUsageGaugeSet());
       // metricRegistry.registerAll(new ThreadStatesGaugeSet());
        metricRegistry.addListener(new LoggingMetricRegistryListener());

  /*    metricRegistry.register("jvm.files", new FileDescriptorRatioGauge());
        metricRegistry.register("jvm.buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));*/

        
        metricRegistry.register(MetricRegistry.name("przodownik", "gauge", "size"), new Gauge<Integer>() {
            Random random = new Random();

            @Override
            public Integer getValue() {
                return  + random.nextInt(1000);
            }
        });
        configureReporters(metricRegistry);
        return metricRegistry;
    }
    
    @Bean
    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }

    @Bean
    public Counter counter() {
        return metricRegistry.counter("simpleCounter");

    }


    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        log.info("+++                                        configureReporters");  
       /* ConsoleReporter.forRegistry(metricRegistry).build().start(10, TimeUnit.SECONDS);*/

        Slf4jReporter.forRegistry(metricRegistry).outputTo(log).convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS).build().start(1, TimeUnit.MINUTES);
        
       /* CsvReporter reporter = CsvReporter.forRegistry(metricRegistry).formatFor(Locale.US).convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS).build(new File("slawek.csv"));
        
        reporter.start(1, TimeUnit.SECONDS);*/
        
        JmxReporter.forRegistry(metricRegistry).build().start();
    }

    @PostConstruct
    private void registerHealthChecks() {
        healthCheckRegistry.register("Metrics HealthCheck mechanism", new BasicHealthCheck());
        healthCheckRegistry.register("Database", new DatabaseHealthCheck(mongo));
        healthCheckRegistry.register("deadlocks", new ThreadDeadlockHealthCheck());
        healthCheckRegistry.register("REST resources", new RestResourcesHealthCheck("http://localhost:8090/api/appContext"));
        healthCheckRegistry.register("disk space check", new DiskCapacityHealthCheck());
        
    }

    @Bean
    @Autowired
    public ServletRegistrationBean servletRegistrationBean(MetricRegistry metricRegistry) {
        MetricsServlet ms = new MetricsServlet(metricRegistry);
        ServletRegistrationBean srb = new ServletRegistrationBean(ms, "/metrics/*");
        srb.setLoadOnStartup(1);
        return srb;
    }
    
    @Bean
    @Autowired
    public ServletRegistrationBean servletHealthRegistryBean(HealthCheckRegistry healthCheckRegistry) {
        HealthCheckServlet hc = new HealthCheckServlet(healthCheckRegistry);
        ServletRegistrationBean srb = new ServletRegistrationBean(hc, "/health/*");
        srb.setLoadOnStartup(2);
        return srb;
    }
    @Bean
    @Autowired
    public ServletRegistrationBean servletPingRegistryBean(HealthCheckRegistry healthCheckRegistry) {
        HealthCheckServlet hc = new HealthCheckServlet(healthCheckRegistry);
        ServletRegistrationBean srb = new ServletRegistrationBean(hc, "/health/*");
        srb.setLoadOnStartup(2);
        return srb;
    }
}