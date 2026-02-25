package com.minibank.service;

import com.minibank.dto.request.UserRequest;
import com.minibank.dto.response.UserResponse;
import com.minibank.entity.User;
import com.minibank.constant.ErrorCode;
import com.minibank.exception.DuplicateResourceException;
import com.minibank.exception.ResourceNotFoundException;
import com.minibank.mapper.UserMapper;
import com.minibank.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

  public List<UserResponse> getAllUsers() {
    return userRepository.findAll().stream()
        .map(userMapper::toResponse)
        .collect(Collectors.toList());
  }

  public UserResponse getUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    return userMapper.toResponse(user);
  }

  @Transactional
  public UserResponse createUser(UserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicateResourceException(
          "Email đã tồn tại: " + request.getEmail(),
          ErrorCode.DUPLICATE_EMAIL
      );
    }
    User user = User.builder()
        .email(request.getEmail())
        .passwordHash(passwordEncoder.encode(request.getPassword()))
        .fullName(request.getFullName())
        .phone(request.getPhone())
        .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
        .build();
    user = userRepository.save(user);
    return userMapper.toResponse(user);
  }

  @Transactional
  public UserResponse updateUser(Long id, UserRequest request) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicateResourceException(
          "Email đã tồn tại: " + request.getEmail(),
          ErrorCode.DUPLICATE_EMAIL
      );
    }
    user.setEmail(request.getEmail());
    if (request.getPassword() != null && !request.getPassword().isBlank()) {
      user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    }
    user.setFullName(request.getFullName());
    user.setPhone(request.getPhone());
    if (request.getStatus() != null) {
      user.setStatus(request.getStatus());
    }
    user = userRepository.save(user);
    return userMapper.toResponse(user);
  }
}
