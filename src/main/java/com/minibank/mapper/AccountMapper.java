package com.minibank.mapper;

import com.minibank.dto.response.AccountResponse;
import com.minibank.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  public AccountResponse toResponse(Account account) {
    if (account == null) {
      return null;
    }
    Long userId = account.getUser() != null ? account.getUser().getId() : null;
    return AccountResponse.builder()
        .id(account.getId())
        .userId(userId)
        .accountNumber(account.getAccountNumber())
        .accountType(account.getAccountType())
        .balance(account.getBalance())
        .currency(account.getCurrency())
        .status(account.getStatus())
        .createdAt(account.getCreatedAt())
        .updatedAt(account.getUpdatedAt())
        .build();
  }
}
