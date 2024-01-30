package com.snakegame.snakegame.config;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusConfig {
    @Bean
    public EventBus boardUpdateEventBus(){
        return new EventBus();
    }
}
