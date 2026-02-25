package com.minibank.exception;

import com.minibank.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private boolean isHtmlRequest(HttpServletRequest request) {
    String accept = request.getHeader("Accept");
    return accept != null && accept.contains("text/html");
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public Object handleResourceNotFound(
      ResourceNotFoundException ex,
      HttpServletRequest request,
      RedirectAttributes redirectAttributes
  ) {
    log.warn("Resource not found: {}", ex.getMessage());
    if (isHtmlRequest(request)) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      return new RedirectView("/", true);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(BusinessException.class)
  public Object handleBusinessException(
      BusinessException ex,
      HttpServletRequest request,
      RedirectAttributes redirectAttributes
  ) {
    log.warn("Business exception: {}", ex.getMessage());
    if (isHtmlRequest(request)) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      String referer = request.getHeader("Referer");
      return new RedirectView(referer != null ? referer : "/", true);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public Object handleDuplicateResource(
      DuplicateResourceException ex,
      HttpServletRequest request,
      RedirectAttributes redirectAttributes
  ) {
    log.warn("Duplicate resource: {}", ex.getMessage());
    if (isHtmlRequest(request)) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      String referer = request.getHeader("Referer");
      return new RedirectView(referer != null ? referer : "/", true);
    }
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationException(
      MethodArgumentNotValidException ex
  ) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());
    log.warn("Validation failed: {}", errors);
    ApiResponse<Void> response = ApiResponse.error("Dữ liệu không hợp lệ", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
  public ResponseEntity<Void> handleNoResourceFound(
      org.springframework.web.servlet.resource.NoResourceFoundException ex
  ) {
    String path = ex.getResourcePath();
    if (path != null && (path.startsWith(".well-known/") || path.contains("devtools"))) {
      return ResponseEntity.notFound().build();
    }
    log.warn("Resource not found: {}", path);
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
    log.error("Unexpected error: ", ex);
    ApiResponse<Void> response = ApiResponse.error("Lỗi hệ thống, vui lòng thử lại sau");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
