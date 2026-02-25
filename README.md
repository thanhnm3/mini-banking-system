# Mini Banking System

Hệ thống ngân hàng mini - học Java Core + Spring Boot.

## Yêu cầu

- Java 17+
- Maven
- Docker (cho PostgreSQL)

## Chạy Database

```bash
docker-compose up -d
```

## Chạy ứng dụng

```bash
mvn spring-boot:run
```

API chạy tại: http://localhost:8080

## Giao diện (Thymeleaf)

| Trang | URL |
|-------|-----|
| Trang chủ | http://localhost:8080/ |
| Danh sách khách hàng | http://localhost:8080/pages/users |
| Chi tiết khách hàng | http://localhost:8080/pages/users/{id} |
| Chi tiết tài khoản | http://localhost:8080/pages/accounts/{id} |
| Chuyển tiền | http://localhost:8080/pages/transactions/transfer |
| Nạp tiền | http://localhost:8080/pages/transactions/deposit |
| Rút tiền | http://localhost:8080/pages/transactions/withdraw |

## API Endpoints

API REST vẫn hoạt động tại /api/v1/...

### Users
| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | /api/v1/users | Danh sách users |
| GET | /api/v1/users/{id} | Chi tiết user |
| POST | /api/v1/users | Tạo user |
| PUT | /api/v1/users/{id} | Cập nhật user |

### Accounts
| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | /api/v1/accounts/user/{userId} | Danh sách TK của user |
| GET | /api/v1/accounts/number/{accountNumber} | Chi tiết TK theo số TK |
| GET | /api/v1/accounts/{id} | Chi tiết TK theo ID |
| POST | /api/v1/accounts | Tạo tài khoản |

### Transactions
| Method | Endpoint | Mô tả |
|--------|----------|-------|
| POST | /api/v1/transactions/transfer | Chuyển tiền |
| POST | /api/v1/transactions/deposit | Nạp tiền |
| POST | /api/v1/transactions/withdraw | Rút tiền |
| GET | /api/v1/transactions/account/{accountId} | Lịch sử giao dịch |

## Cấu trúc project

```
src/main/java/com/minibank/
├── config/          # Cấu hình (PasswordEncoder)
├── constant/        # Hằng số (ApiPath, ErrorCode)
├── controller/      # REST Controllers
├── dto/             # Request/Response DTOs
├── entity/          # JPA Entities
├── exception/       # Custom exceptions + GlobalExceptionHandler
├── mapper/          # Entity ↔ DTO mappers
├── repository/      # JPA Repositories
└── service/         # Business logic
```
