package com.payex.demo.shoppingcart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

public class Cart {
  @Id
  public String id;
  public List<String> items = new ArrayList<>();
  @JsonIgnore
  public long ownerId;

  public Cart() {
  }

  public Cart(final long ownerId) {
    this.ownerId = ownerId;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public List<String> getItems() {
    return items;
  }

  public void setItems(final List<String> items) {
    this.items = items;
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(final long ownerId) {
    this.ownerId = ownerId;
  }
}
