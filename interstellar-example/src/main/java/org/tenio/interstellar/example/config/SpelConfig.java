package org.tenio.interstellar.example.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
public class SpelConfig {

    @Bean
    public SpelExpressionParser spelExpressionParser(ApplicationContext applicationContext) {
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
        return new SpelExpressionParser(config);
    }
}
