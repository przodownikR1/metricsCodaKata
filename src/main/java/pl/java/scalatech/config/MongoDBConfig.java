package pl.java.scalatech.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configurable
@PropertySource("classpath:mongo.properties")
@PropertySource(value = "file:${CONF_DIR}/optional-mongo.properties", ignoreResourceNotFound = true)
@Slf4j
public class MongoDBConfig extends AbstractMongoConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${mongoDBName}")
    private String mongoDBName;

    @Value("${mongoPort}")
    private String mongoPort;

   
    @Bean
    public DB mongoDB() throws Exception {
        return mongo().getDB(mongoDBName);
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(mongoPort);
    }

    @Override
    public String getDatabaseName() {
        return mongoDBName;
    }

  

}