package com.minibank.controller;

import com.minibank.dto.request.DepositRequest;
import com.minibank.dto.request.TransferRequest;
import com.minibank.dto.request.WithdrawRequest;
import com.minibank.dto.response.AccountResponse;
import com.minibank.exception.BusinessException;
import com.minibank.exception.DuplicateResourceException;
import com.minibank.exception.ResourceNotFoundException;
import com.minibank.service.AccountService;
import com.minibank.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pages")
@RequiredArgsConstructor
public class TransactionPageController {

  private final TransactionService transactionService;
  private final AccountService accountService;

  @ModelAttribute("allAccounts")
  public List<AccountResponse> getAllAccountsForDropdown() {
    return accountService.getAllAccounts().stream()
        .filter(a -> "ACTIVE".equals(a.getStatus()))
        .toList();
  }

  @GetMapping("/transactions/transfer")
  public String transferForm(Model model) {
    model.addAttribute("transferRequest", new TransferRequest());
    return "transactions/transfer";
  }

  @PostMapping("/transactions/transfer")
  public String transferSubmit(
      @Valid @ModelAttribute("transferRequest") TransferRequest request,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    if (bindingResult.hasErrors()) {
      return "transactions/transfer";
    }
    try {
      transactionService.transfer(request);
      redirectAttributes.addFlashAttribute("successMessage", "Chuyển tiền thành công");
      return "redirect:/pages/transactions/transfer";
    } catch (ResourceNotFoundException | BusinessException | DuplicateResourceException ex) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      return "redirect:/pages/transactions/transfer";
    }
  }

  @GetMapping("/transactions/deposit")
  public String depositForm(Model model) {
    model.addAttribute("depositRequest", new DepositRequest());
    return "transactions/deposit";
  }

  @PostMapping("/transactions/deposit")
  public String depositSubmit(
      @Valid @ModelAttribute("depositRequest") DepositRequest request,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    if (bindingResult.hasErrors()) {
      return "transactions/deposit";
    }
    try {
      transactionService.deposit(request);
      redirectAttributes.addFlashAttribute("successMessage", "Nạp tiền thành công");
      return "redirect:/pages/transactions/deposit";
    } catch (ResourceNotFoundException | BusinessException | DuplicateResourceException ex) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      return "redirect:/pages/transactions/deposit";
    }
  }

  @GetMapping("/transactions/withdraw")
  public String withdrawForm(Model model) {
    model.addAttribute("withdrawRequest", new WithdrawRequest());
    return "transactions/withdraw";
  }

  @PostMapping("/transactions/withdraw")
  public String withdrawSubmit(
      @Valid @ModelAttribute("withdrawRequest") WithdrawRequest request,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    if (bindingResult.hasErrors()) {
      return "transactions/withdraw";
    }
    try {
      transactionService.withdraw(request);
      redirectAttributes.addFlashAttribute("successMessage", "Rút tiền thành công");
      return "redirect:/pages/transactions/withdraw";
    } catch (ResourceNotFoundException | BusinessException | DuplicateResourceException ex) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      return "redirect:/pages/transactions/withdraw";
    }
  }
}
