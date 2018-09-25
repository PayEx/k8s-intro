package com.payex.demo.shoppingcart.service;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpMethod.GET;

import com.payex.demo.shoppingcart.domain.Customer;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
  private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
  private final RestTemplate customerRegistry;

  public CustomerService(RestTemplate customerRegistry) {
    this.customerRegistry = customerRegistry;
  }

  public Optional<Customer> findById(long id) {
    try {
      return Optional.ofNullable(customerRegistry.getForObject("/api/customers/" + id, Customer.class));
    } catch (Exception ioe) {
      log.info("Caught exception while fetching customer {}", id, ioe);
    }

    return Optional.empty();
  }

  public List<Customer> findAll() {
    try {
      final ResponseEntity<List<Customer>> customers = customerRegistry.exchange(
          "/api/customers", GET, null, new ParameterizedTypeReference<List<Customer>>() {}
      );

      if (customers.hasBody()) {
        return customers.getBody();
      }

    } catch (Exception ioe) {
      log.info("Caught exception while fetching all customers", ioe);
    }

    return emptyList();
  }

  public boolean isAvailable() {
    final ResponseEntity<String> status = customerRegistry.getForEntity("/health", String.class);
    return status.getStatusCode().is2xxSuccessful();
  }
}
