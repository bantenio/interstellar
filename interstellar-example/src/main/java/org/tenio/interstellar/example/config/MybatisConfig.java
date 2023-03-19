package org.tenio.interstellar.example.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("org.tenio.interstellar.example.**.dao")
public class MybatisConfig {
}
