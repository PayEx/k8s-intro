package com.payex.demo.shoppingcart;

import com.payex.demo.shoppingcart.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class ShoppingCartApp {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApp.class, args);
	}
}
