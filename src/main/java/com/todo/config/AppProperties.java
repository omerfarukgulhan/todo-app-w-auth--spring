package com.todo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "todo-app")
@Configuration
public class AppProperties {

    private Email email;

    private Client client;

    private Storage storage = new Storage();

    private String tokenType;

    public record Email(
            String username,
            String password,
            String host,
            int port,
            String from
    ) {
    }

    public record Client(
            String host
    ) {
    }

    @Data
    public static class Storage {
        private String root = "uploads";

        private String profile = "profile";
    }
}
