package com.payex.demo.shoppingcart.service;

import com.payex.demo.shoppingcart.domain.Cart;
import com.payex.demo.shoppingcart.domain.CartRepository;
import com.payex.demo.shoppingcart.domain.Customer;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
  private static final Logger log = LoggerFactory.getLogger(CartService.class);

  private final CustomerService customerService;
  private final CartRepository repo;

  @Autowired
  public CartService(CustomerService customerService, CartRepository repo) {
    this.customerService = customerService;
    this.repo = repo;
  }

  public Cart findForCustomerId(long id) {
    Customer customer = customerService
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Could not look up customer info"));

    return repo
        .findCartByOwnerId(customer.getId())
        .orElseGet(() -> repo.save(new Cart(customer.getId())));
  }

  public Cart addToCart(Cart cart, String item) {
    Objects.requireNonNull(item, "Item is required");

    for(String existing : cart.items) {
      if (existing.equalsIgnoreCase(item)) {
        log.info("{} already exists, won't add", item);
        return cart;
      }
    }

    cart.items.add(item);

    return repo.save(cart);
  }

  public Cart removeFromCart(Cart cart, String item) {
    Objects.requireNonNull(item, "Item is required");
    cart.items.removeIf(currentItem -> currentItem.equalsIgnoreCase(item));

    return repo.save(cart);
  }
}
