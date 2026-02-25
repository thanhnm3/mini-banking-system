package com.minibank.mapper;

import com.minibank.dto.response.TransactionResponse;
import com.minibank.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  public TransactionResponse toResponse(Transaction transaction) {
    if (transaction == null) {
      return null;
    }
    Long fromAccountId = transaction.getFromAccount() != null
        ? transaction.getFromAccount().getId()
        : null;
    Long toAccountId = transaction.getToAccount() != null
        ? transaction.getToAccount().getId()
        : null;
    return TransactionResponse.builder()
        .id(transaction.getId())
        .fromAccountId(fromAccountId)
        .toAccountId(toAccountId)
        .amount(transaction.getAmount())
        .type(transaction.getType())
        .description(transaction.getDescription())
        .status(transaction.getStatus())
        .createdAt(transaction.getCreatedAt())
        .build();
  }
}
