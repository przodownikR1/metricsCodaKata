package pl.java.scalatech.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@Configurable
@EnableMongoRepositories(basePackages="pl.java.scalatech.repository")

//@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class MongoRepositoryConfig {

}
