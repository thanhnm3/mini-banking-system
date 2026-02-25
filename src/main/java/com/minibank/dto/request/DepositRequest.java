package com.minibank.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
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
public class DepositRequest {

  @NotNull(message = "Tài khoản đích không được để trống")
  @Positive(message = "Tài khoản đích phải là số dương")
  private Long toAccountId;

  @NotNull(message = "Số tiền không được để trống")
  @Positive(message = "Số tiền phải lớn hơn 0")
  private BigDecimal amount;

  @Size(max = 255)
  private String description;
}
