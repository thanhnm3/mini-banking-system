package com.minibank.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Xử lý request favicon.ico để tránh NoResourceFoundException.
 */
@Controller
public class FaviconController {

  @GetMapping("/favicon.ico")
  public ResponseEntity<Resource> favicon() {
    Resource resource = new ClassPathResource("static/favicon.svg");
    if (resource.exists()) {
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType("image/svg+xml"))
          .body(resource);
    }
    return ResponseEntity.noContent().build();
  }
}
