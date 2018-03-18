package com.codingchallenge.subscriptionsystem.eventservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.codingchallenge.subscriptionsystem")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
