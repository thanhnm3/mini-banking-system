package com.minibank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class ApiTestPageController {

  @GetMapping("/api-test")
  public String apiTest() {
    return "api-test";
  }
}
