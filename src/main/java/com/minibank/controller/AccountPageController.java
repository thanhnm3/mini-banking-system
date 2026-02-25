package com.minibank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
@RequiredArgsConstructor
public class AccountPageController {

  @GetMapping("/accounts/{id}")
  public String accountDetail(@PathVariable Long id, Model model) {
    model.addAttribute("id", id);
    return "accounts/detail";
  }
}
