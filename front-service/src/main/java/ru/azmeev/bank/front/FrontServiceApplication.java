package ru.azmeev.bank.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FrontServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontServiceApplication.class, args);
    }

}
