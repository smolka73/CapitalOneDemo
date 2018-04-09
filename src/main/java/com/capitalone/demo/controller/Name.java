package com.capitalone.demo.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Name {

  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z -]{3,128}$", message = "Only characters are allowed, the length must be between 3 and 128 characters")
  private String name;

  @JsonCreator
  Name(@JsonProperty("name") String name) {
    this.name = name;
  }

  String getName() {
    return name;
  }

  void putName(String name) {
    this.name = name;
  }
}
