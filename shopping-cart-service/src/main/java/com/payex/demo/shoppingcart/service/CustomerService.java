package com.payex.demo.shoppingcart.service;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import com.payex.demo.shoppingcart.domain.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
  private final WebClient customerRegistry;

  public CustomerService(WebClient customerRegistry) {
    this.customerRegistry = customerRegistry;
  }

  public Optional<Customer> findById(long id) {
    return customerRegistry.get()
        .uri("/api/customers/" + id)
        .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
        .retrieve()
        .onStatus(
            httpStatus -> httpStatus.equals(HttpStatus.NOT_FOUND),
            clientResponse -> Mono.justOrEmpty(new IllegalArgumentException("Customer with id " + id + " doesn't exist")))
        .bodyToMono(Customer.class)
        .blockOptional();
  }

  public List<Customer> findAll() {
    return customerRegistry.get()
        .uri("/api/customers")
        .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
        .retrieve()
        .bodyToFlux(Customer.class)
        .collectList()
        .block();
  }

}
