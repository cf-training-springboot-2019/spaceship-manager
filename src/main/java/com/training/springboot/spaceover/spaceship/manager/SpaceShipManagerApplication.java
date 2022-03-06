package com.training.springboot.spaceover.spaceship.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpaceShipManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceShipManagerApplication.class, args);
    }

}
