package com.training.springboot.spaceover.spaceship.manager.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
@RequiredArgsConstructor
public class SpaceshipManagerWebConfiguration {


    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

}