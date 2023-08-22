package com.example.FinalProject.configuration;

import com.example.FinalProject.model.AlphaVantageAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlphaVantageConfig {
    @Bean
    public AlphaVantageAPI alphaVantageAPIExample() {
        return new AlphaVantageAPI();
    }
}
