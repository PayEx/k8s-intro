package com.payex.demo.shoppingcart.dto;

public class ItemRequest {
  private String item;

  public String getItem() {
    return item;
  }

  public void setItem(final String item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "'" + item + "'";
  }
}
