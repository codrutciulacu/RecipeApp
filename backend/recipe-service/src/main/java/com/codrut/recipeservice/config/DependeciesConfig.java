package com.codrut.recipeservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependeciesConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
