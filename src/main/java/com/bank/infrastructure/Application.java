package com.bank.infrastructure;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.data.repository.query.QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

@SpringBootApplication
@EnableJpaRepositories(value = "com.bank.repository", queryLookupStrategy = CREATE_IF_NOT_FOUND)
@EntityScan("com.bank.entity")
@ComponentScan({"com.bank"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        migrateDB();
    }

    private static void migrateDB() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://localhost/bankApp", "root", null);
        flyway.migrate();
    }
}
