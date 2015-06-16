package pl.java.scalatech.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "pl.java.scalatech.service"}, useDefaultFilters = false, includeFilters = {
        @Filter(Service.class), @Filter(Component.class) })
@EnableTransactionManagement
public class ServiceConfig {

}
