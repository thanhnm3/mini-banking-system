package com.minibank.controller;

import com.minibank.constant.ApiPath;
import com.minibank.dto.ApiResponse;
import com.minibank.dto.request.DepositRequest;
import com.minibank.dto.request.TransferRequest;
import com.minibank.dto.request.WithdrawRequest;
import com.minibank.dto.response.TransactionResponse;
import com.minibank.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.TRANSACTIONS)
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping("/transfer")
  public ResponseEntity<ApiResponse<TransactionResponse>> transfer(
      @Valid @RequestBody TransferRequest request
  ) {
    TransactionResponse transaction = transactionService.transfer(request);
    return ResponseEntity.ok(ApiResponse.success(transaction, "Chuyển tiền thành công"));
  }

  @PostMapping("/deposit")
  public ResponseEntity<ApiResponse<TransactionResponse>> deposit(
      @Valid @RequestBody DepositRequest request
  ) {
    TransactionResponse transaction = transactionService.deposit(request);
    return ResponseEntity.ok(ApiResponse.success(transaction, "Nạp tiền thành công"));
  }

  @PostMapping("/withdraw")
  public ResponseEntity<ApiResponse<TransactionResponse>> withdraw(
      @Valid @RequestBody WithdrawRequest request
  ) {
    TransactionResponse transaction = transactionService.withdraw(request);
    return ResponseEntity.ok(ApiResponse.success(transaction, "Rút tiền thành công"));
  }

  @GetMapping("/account/{accountId}")
  public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionHistory(
      @PathVariable Long accountId
  ) {
    List<TransactionResponse> transactions = transactionService.getTransactionHistory(accountId);
    return ResponseEntity.ok(ApiResponse.success(transactions));
  }
}
