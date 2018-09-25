package com.payex.demo.shoppingcart.config;

import com.payex.demo.shoppingcart.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("customerServiceAvailability")
public class CustomerServiceCheck implements HealthIndicator {
  private final CustomerService service;

  @Autowired
  public CustomerServiceCheck(CustomerService service) {
    this.service = service;
  }

  @Override
  public Health health() {
    final boolean available = service.isAvailable();

    if (available) {
      return Health.up().build();
    }

    return Health.down().build();
  }
}
