package com.example.mspin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPinApplication.class, args);
    }
}
