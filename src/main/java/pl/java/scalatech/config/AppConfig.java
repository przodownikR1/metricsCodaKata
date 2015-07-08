package pl.java.scalatech.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(basePackages = { "pl.java.scalatech.service", "pl.java.scalatech.web.controller", "pl.java.scalatech.component", "pl.java.scalatech.config" }, useDefaultFilters = false, includeFilters = {
        @Filter(Service.class), @Filter(Component.class) })
@EnableAutoConfiguration
public class AppConfig {

}