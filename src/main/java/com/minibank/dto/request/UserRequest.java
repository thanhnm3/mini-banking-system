package com.minibank.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UserRequest {

  @NotBlank(message = "Email không được để trống")
  @Email(message = "Email không hợp lệ")
  @Size(max = 255)
  private String email;

  @NotBlank(message = "Mật khẩu không được để trống")
  @Size(min = 6, max = 255)
  private String password;

  @Size(max = 100)
  private String fullName;

  @Size(max = 20)
  private String phone;

  @Size(max = 20)
  private String status;
}
