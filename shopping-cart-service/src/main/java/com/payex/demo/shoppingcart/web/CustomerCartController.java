package com.payex.demo.shoppingcart.web;

import com.payex.demo.shoppingcart.domain.Cart;
import com.payex.demo.shoppingcart.dto.ItemRequest;
import com.payex.demo.shoppingcart.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerCartController {
  private static final Logger log = LoggerFactory.getLogger(CustomerCartController.class);
  private static final String ID_CART = "/api/customers/{id}/cart";

  private final CartService cartService;

  @Autowired
  public CustomerCartController(final CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping(ID_CART)
  public Cart getCartForCustomer(@PathVariable("id") Long id) {
    log.info("Finding cart for customer {}", id);
    return cartService.findForCustomerId(id);
  }

  @PostMapping(ID_CART)
  public Cart addItemToCart(@PathVariable("id") Long id, @RequestBody ItemRequest req) {
    log.info("Trying to add {} to customer {}'s cart", req, id);
    Cart cart = cartService.findForCustomerId(id);

    return cartService.addToCart(cart, req.getItem());
  }

  @DeleteMapping(ID_CART)
  public Cart removeItemFromCart(@PathVariable("id") Long id, @RequestBody ItemRequest req) {
    log.info("Trying to remove {} from customer {}'s cart", req, id);
    Cart cart = cartService.findForCustomerId(id);

    return cartService.removeFromCart(cart, req.getItem());
  }
}
