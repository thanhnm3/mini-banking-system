package com.minibank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class AccountRequest {

  @NotNull(message = "User ID không được để trống")
  @Positive(message = "User ID phải là số dương")
  private Long userId;

  @NotBlank(message = "Số tài khoản không được để trống")
  @Size(max = 20)
  private String accountNumber;

  @Size(max = 50)
  private String accountType;

  @Size(max = 3)
  private String currency;

  @Size(max = 20)
  private String status;
}
