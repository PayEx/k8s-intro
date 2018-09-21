package com.payex.demo.shoppingcart.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceCommunication {
  @Bean
  RestTemplate customerRegistry(RestTemplateBuilder builder, AppConfig config) {
    return builder
        .rootUri(config.getCustomerRegistryBaseUrl())
        .build();
  }
}
