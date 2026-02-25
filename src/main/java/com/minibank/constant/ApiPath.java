package com.minibank.constant;

/**
 * Đường dẫn API (Rule 25: tên router cho vào file constant).
 */
public final class ApiPath {

  private ApiPath() {
  }

  public static final String API_V1 = "/api/v1";
  public static final String USERS = API_V1 + "/users";
  public static final String ACCOUNTS = API_V1 + "/accounts";
  public static final String TRANSACTIONS = API_V1 + "/transactions";
}
