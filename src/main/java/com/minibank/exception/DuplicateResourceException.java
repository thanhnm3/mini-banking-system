package com.minibank.exception;

import com.minibank.constant.ErrorCode;

public class DuplicateResourceException extends RuntimeException {

  private final String errorCode;

  public DuplicateResourceException(String message) {
    super(message);
    this.errorCode = ErrorCode.DUPLICATE_EMAIL;
  }

  public DuplicateResourceException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
