package com.akhil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping
  public String homecontroller() {
    return "Welcome to HomeController";
  }
}
