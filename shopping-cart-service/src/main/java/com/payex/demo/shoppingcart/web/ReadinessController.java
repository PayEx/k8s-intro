package com.payex.demo.shoppingcart.web;

import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;

import com.payex.demo.shoppingcart.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/readiness")
@RestController
public class ReadinessController {
  @Autowired
  CustomerService service;

  @GetMapping
  public ResponseEntity<String> checkReadiness() {
    final boolean available = service.isAvailable();

    if (available) {
      return ResponseEntity.ok("Send me requests please!");
    }

    return ResponseEntity
        .status(GATEWAY_TIMEOUT)
        .body("I am having trouble processing requests, please check again soon");
  }
}
