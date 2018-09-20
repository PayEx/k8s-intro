package com.payex.demo.shoppingcart.domain;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
  Optional<Cart> findCartByOwnerId(long ownerId);
}
