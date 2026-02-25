package com.minibank.constant;

/**
 * Mã lỗi dùng cho exception và response.
 */
public final class ErrorCode {

  private ErrorCode() {
  }

  public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
  public static final String ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";
  public static final String TRANSACTION_NOT_FOUND = "TRANSACTION_NOT_FOUND";
  public static final String DUPLICATE_EMAIL = "DUPLICATE_EMAIL";
  public static final String DUPLICATE_ACCOUNT_NUMBER = "DUPLICATE_ACCOUNT_NUMBER";
  public static final String INSUFFICIENT_BALANCE = "INSUFFICIENT_BALANCE";
  public static final String ACCOUNT_INACTIVE = "ACCOUNT_INACTIVE";
  public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
}
