package com.payex.demo.shoppingcart.config;

import javax.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("cart")
public class AppConfig {
  @NotEmpty(message = "A valid customer registry base URL is required")
  private String customerRegistryBaseUrl;

  public String getCustomerRegistryBaseUrl() {
    return customerRegistryBaseUrl;
  }

  public void setCustomerRegistryBaseUrl(final String customerRegistryBaseUrl) {
    this.customerRegistryBaseUrl = customerRegistryBaseUrl;
  }
}
