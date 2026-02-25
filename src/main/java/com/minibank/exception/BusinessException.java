package com.minibank.exception;

import com.minibank.constant.ErrorCode;

public class BusinessException extends RuntimeException {

  private final String errorCode;

  public BusinessException(String message) {
    super(message);
    this.errorCode = ErrorCode.VALIDATION_ERROR;
  }

  public BusinessException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
