package pl.java.scalatech.config.metrics;

import static java.util.stream.Collectors.joining;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.Mongo;

@Slf4j
public class DatabaseHealthCheck extends HealthCheck {

    private Mongo mongo;

    public DatabaseHealthCheck(Mongo mongo) {
        this.mongo = mongo;
    }

    @Override
    protected Result check() throws Exception {

        List<String> dbs = mongo.getDatabaseNames();
        String dbsString = dbs.stream().collect(joining(", "));
        return HealthCheck.Result.healthy("Dbs : " + dbsString);

    }
}