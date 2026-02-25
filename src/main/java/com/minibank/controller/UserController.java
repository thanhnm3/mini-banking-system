package com.minibank.controller;

import com.minibank.constant.ApiPath;
import com.minibank.dto.ApiResponse;
import com.minibank.dto.request.UserRequest;
import com.minibank.dto.response.UserResponse;
import com.minibank.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.USERS)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
    List<UserResponse> users = userService.getAllUsers();
    return ResponseEntity.ok(ApiResponse.success(users));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
    UserResponse user = userService.getUserById(id);
    return ResponseEntity.ok(ApiResponse.success(user));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<UserResponse>> createUser(
      @Valid @RequestBody UserRequest request
  ) {
    UserResponse user = userService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(user, "Tạo user thành công"));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<UserResponse>> updateUser(
      @PathVariable Long id,
      @Valid @RequestBody UserRequest request
  ) {
    UserResponse user = userService.updateUser(id, request);
    return ResponseEntity.ok(ApiResponse.success(user, "Cập nhật user thành công"));
  }
}
