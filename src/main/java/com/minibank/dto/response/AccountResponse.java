package com.minibank.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

  private Long id;
  private Long userId;
  private String accountNumber;
  private String accountType;
  private BigDecimal balance;
  private String currency;
  private String status;
  private Instant createdAt;
  private Instant updatedAt;
}
