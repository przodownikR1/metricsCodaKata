package pl.java.scalatech.config;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Slf4j
public class MailConfiguration implements EnvironmentAware {


    
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String PROP_HOST = "spring.mail.host";
    private static final String DEFAULT_PROP_HOST = "localhost";
    private static final String PROP_PORT = "spring.mail.port";
    private static final String PROP_USER = "spring.mail.user";
    private static final String PROP_PASSWORD = "spring.mail.password";
    private static final String PROP_PROTO = "spring.mail.protocol";
    private static final String PROP_TLS = "spring.mail.tls";
    private static final String PROP_AUTH = "spring.mail.auth";
    private static final String PROP_SMTP_AUTH = "mail.smtp.auth";
    private static final String PROP_STARTTLS = "mail.smtp.starttls.enable";
    private static final String PROP_TRANSPORT_PROTO = "mail.transport.protocol";
    
    private RelaxedPropertyResolver propertyResolver;

    public MailConfiguration() {
    }

    

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        log.debug("Configuring mail server");
        String host = propertyResolver.getProperty(PROP_HOST, DEFAULT_PROP_HOST);
        int port = propertyResolver.getProperty(PROP_PORT, Integer.class, 0);
        String user = propertyResolver.getProperty(PROP_USER);
        String password = propertyResolver.getProperty(PROP_PASSWORD);
        String protocol = propertyResolver.getProperty(PROP_PROTO);
        Boolean tls = propertyResolver.getProperty(PROP_TLS, Boolean.class, false);
        Boolean auth = propertyResolver.getProperty(PROP_AUTH, Boolean.class, false);
        System.err.println(host + " " + port + " " + user + "  " + password + " " + protocol + "  " + tls + " " + auth);
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        if (host != null && !host.isEmpty()) {
            sender.setHost(host);
        } else {
            log.warn("Warning! Your SMTP server is not configured. We will try to use one on localhost.");
            log.debug("Did you configure your SMTP settings in your application.yml?");
            sender.setHost(DEFAULT_HOST);
        }
        sender.setPort(port);
        sender.setUsername(user);
        sender.setPassword(password);
        Properties sendProperties = new Properties();
        sendProperties.setProperty(PROP_SMTP_AUTH, auth.toString());
        sendProperties.setProperty(PROP_STARTTLS, tls.toString());
      //  sendProperties.setProperty(PROP_TRANSPORT_PROTO, protocol);
        sender.setJavaMailProperties(sendProperties);
        return sender;
    }



    @Override
    public void setEnvironment(Environment environment) {
        System.err.println("_++++++++++++++++++");
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.mail.");
        
    }
}