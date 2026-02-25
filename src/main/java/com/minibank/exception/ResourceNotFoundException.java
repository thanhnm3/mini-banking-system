package com.minibank.exception;

import com.minibank.constant.ErrorCode;

public class ResourceNotFoundException extends RuntimeException {

  private final String errorCode;

  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s không tìm thấy với %s: '%s'", resourceName, fieldName, fieldValue));
    this.errorCode = getErrorCodeByResource(resourceName);
  }

  public String getErrorCode() {
    return errorCode;
  }

  private static String getErrorCodeByResource(String resourceName) {
    return switch (resourceName) {
      case "User" -> ErrorCode.USER_NOT_FOUND;
      case "Account" -> ErrorCode.ACCOUNT_NOT_FOUND;
      case "Transaction" -> ErrorCode.TRANSACTION_NOT_FOUND;
      default -> "RESOURCE_NOT_FOUND";
    };
  }
}
