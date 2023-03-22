package org.tenio.interstellar.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tenio.interstellar.jackson.ObjectMapperFactory;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.objectMapper();
    }
}
