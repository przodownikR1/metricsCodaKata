package pl.java.scalatech.config;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = { "pl.java.scalatech.setting" }, useDefaultFilters = false, includeFilters = { @Filter(Component.class) })
// @Profile(value={"dev","test"})
public class PropConfig {

    @Bean
    public PropertySource<?> yamlPropertySourceLoader() throws IOException {
        
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        PropertySource<?> applicationYamlPropertySource = loader.load("application.yml", new ClassPathResource("application.yml"), "default");
        return applicationYamlPropertySource;

    }

}