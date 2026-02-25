package com.minibank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Xử lý request .well-known từ browser/DevTools để tránh NoResourceFoundException.
 */
@RestController
@RequestMapping("/.well-known")
public class WellKnownController {

  @GetMapping(value = "/**", produces = "application/json")
  public ResponseEntity<Void> wellKnown() {
    return ResponseEntity.notFound().build();
  }
}
