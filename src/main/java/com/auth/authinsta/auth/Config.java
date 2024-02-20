package com.auth.authinsta.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public JwtManager createJwtManager() {
        return new JwtManager();
    }

    @Bean
    public UserContext createUserContext() {
        return new UserContext();
    }
}
