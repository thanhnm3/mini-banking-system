package com.minibank.controller;

import com.minibank.constant.ApiPath;
import com.minibank.dto.ApiResponse;
import com.minibank.dto.request.AccountRequest;
import com.minibank.dto.response.AccountResponse;
import com.minibank.service.AccountService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.ACCOUNTS)
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccountsByUserId(
      @PathVariable Long userId
  ) {
    List<AccountResponse> accounts = accountService.getAccountsByUserId(userId);
    return ResponseEntity.ok(ApiResponse.success(accounts));
  }

  @GetMapping("/number/{accountNumber}")
  public ResponseEntity<ApiResponse<AccountResponse>> getAccountByAccountNumber(
      @PathVariable String accountNumber
  ) {
    AccountResponse account = accountService.getAccountByAccountNumber(accountNumber);
    return ResponseEntity.ok(ApiResponse.success(account));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long id) {
    AccountResponse account = accountService.getAccountById(id);
    return ResponseEntity.ok(ApiResponse.success(account));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
      @Valid @RequestBody AccountRequest request
  ) {
    AccountResponse account = accountService.createAccount(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(account, "Tạo tài khoản thành công"));
  }
}
