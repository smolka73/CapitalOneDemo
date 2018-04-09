package com.capitalone.demo.controller;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Amount {

  @Min(message = "Amount must be >= 0", value = 0)
  private final int amount;

  @JsonCreator
  Amount(@JsonProperty("amount") int amount) {
    this.amount = amount;
  }

  int getAmount() {
    return amount;
  }
}
