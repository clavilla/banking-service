package com.banking.bankingservice.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.banking.bankingservice.domain.repository")
public class H2Config {
}
