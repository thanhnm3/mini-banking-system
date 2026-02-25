package com.minibank.service;

import com.minibank.dto.request.DepositRequest;
import com.minibank.dto.request.TransferRequest;
import com.minibank.dto.request.WithdrawRequest;
import com.minibank.dto.response.TransactionResponse;
import com.minibank.entity.Account;
import com.minibank.entity.Transaction;
import com.minibank.constant.ErrorCode;
import com.minibank.exception.BusinessException;
import com.minibank.exception.ResourceNotFoundException;
import com.minibank.mapper.TransactionMapper;
import com.minibank.repository.AccountRepository;
import com.minibank.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private static final String STATUS_ACTIVE = "ACTIVE";
  private static final String TYPE_TRANSFER = "TRANSFER";
  private static final String TYPE_DEPOSIT = "DEPOSIT";
  private static final String TYPE_WITHDRAW = "WITHDRAW";
  private static final String STATUS_SUCCESS = "SUCCESS";

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final TransactionMapper transactionMapper;

  @Transactional
  public TransactionResponse transfer(TransferRequest request) {
    Account fromAccount = getActiveAccount(request.getFromAccountId());
    Account toAccount = getActiveAccount(request.getToAccountId());
    if (fromAccount.getId().equals(toAccount.getId())) {
      throw new BusinessException("Không thể chuyển tiền vào chính tài khoản nguồn");
    }
    validateBalance(fromAccount, request.getAmount());
    fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
    toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);
    Transaction transaction = Transaction.builder()
        .fromAccount(fromAccount)
        .toAccount(toAccount)
        .amount(request.getAmount())
        .type(TYPE_TRANSFER)
        .description(request.getDescription())
        .status(STATUS_SUCCESS)
        .build();
    transaction = transactionRepository.save(transaction);
    return transactionMapper.toResponse(transaction);
  }

  @Transactional
  public TransactionResponse deposit(DepositRequest request) {
    Account toAccount = getActiveAccount(request.getToAccountId());
    toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
    accountRepository.save(toAccount);
    Transaction transaction = Transaction.builder()
        .fromAccount(null)
        .toAccount(toAccount)
        .amount(request.getAmount())
        .type(TYPE_DEPOSIT)
        .description(request.getDescription())
        .status(STATUS_SUCCESS)
        .build();
    transaction = transactionRepository.save(transaction);
    return transactionMapper.toResponse(transaction);
  }

  @Transactional
  public TransactionResponse withdraw(WithdrawRequest request) {
    Account fromAccount = getActiveAccount(request.getFromAccountId());
    validateBalance(fromAccount, request.getAmount());
    fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
    accountRepository.save(fromAccount);
    Transaction transaction = Transaction.builder()
        .fromAccount(fromAccount)
        .toAccount(null)
        .amount(request.getAmount())
        .type(TYPE_WITHDRAW)
        .description(request.getDescription())
        .status(STATUS_SUCCESS)
        .build();
    transaction = transactionRepository.save(transaction);
    return transactionMapper.toResponse(transaction);
  }

  public List<TransactionResponse> getTransactionHistory(Long accountId) {
    return transactionRepository.findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(
      accountId,
      accountId
    ).stream()
        .map(transactionMapper::toResponse)
        .collect(Collectors.toList());
  }

  private Account getActiveAccount(Long accountId) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
    if (!STATUS_ACTIVE.equals(account.getStatus())) {
      throw new BusinessException(
          "Tài khoản không hoạt động: " + account.getAccountNumber(),
          ErrorCode.ACCOUNT_INACTIVE
      );
    }
    return account;
  }

  private void validateBalance(Account account, BigDecimal amount) {
    if (account.getBalance().compareTo(amount) < 0) {
      throw new BusinessException(
          "Số dư không đủ. Số dư hiện tại: " + account.getBalance(),
          ErrorCode.INSUFFICIENT_BALANCE
      );
    }
  }
}
