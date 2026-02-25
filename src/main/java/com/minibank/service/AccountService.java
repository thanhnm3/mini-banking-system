package com.minibank.service;

import com.minibank.dto.request.AccountRequest;
import com.minibank.dto.response.AccountResponse;
import com.minibank.entity.Account;
import com.minibank.entity.User;
import com.minibank.constant.ErrorCode;
import com.minibank.exception.DuplicateResourceException;
import com.minibank.exception.ResourceNotFoundException;
import com.minibank.mapper.AccountMapper;
import com.minibank.repository.AccountRepository;
import com.minibank.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final AccountMapper accountMapper;

  public List<AccountResponse> getAllAccounts() {
    return accountRepository.findAll().stream()
        .map(accountMapper::toResponse)
        .collect(Collectors.toList());
  }

  public List<AccountResponse> getAccountsByUserId(Long userId) {
    return accountRepository.findByUser_Id(userId).stream()
        .map(accountMapper::toResponse)
        .collect(Collectors.toList());
  }

  public AccountResponse getAccountByAccountNumber(String accountNumber) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber));
    return accountMapper.toResponse(account);
  }

  public AccountResponse getAccountById(Long id) {
    Account account = accountRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    return accountMapper.toResponse(account);
  }

  @Transactional
  public AccountResponse createAccount(AccountRequest request) {
    if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
      throw new DuplicateResourceException(
          "Số tài khoản đã tồn tại: " + request.getAccountNumber(),
          ErrorCode.DUPLICATE_ACCOUNT_NUMBER
      );
    }
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));
    Account account = Account.builder()
        .user(user)
        .accountNumber(request.getAccountNumber())
        .accountType(request.getAccountType() != null ? request.getAccountType() : "CHECKING")
        .currency(request.getCurrency() != null ? request.getCurrency() : "VND")
        .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
        .build();
    account = accountRepository.save(account);
    return accountMapper.toResponse(account);
  }
}
