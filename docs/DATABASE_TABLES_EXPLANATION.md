# Giải thích chi tiết các bảng trong Mini Banking System

## Tổng quan

Database được thiết kế theo mô hình chuẩn, phù hợp cho việc học Java Core + Spring Boot. Phase 1 gồm 3 bảng chính.

---

## 1. Bảng `users`

### Nhiệm vụ
Lưu thông tin **người dùng** (khách hàng) của hệ thống ngân hàng. Mỗi user có thể đăng nhập, quản lý tài khoản và thực hiện giao dịch.

### Mô tả chi tiết từng cột

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| `id` | BIGINT | Khóa chính, tự tăng. Định danh duy nhất mỗi user. |
| `email` | VARCHAR(255) | Email đăng nhập. **Unique** - không trùng lặp. |
| `password_hash` | VARCHAR(255) | Mật khẩu đã mã hóa (bcrypt/Argon2). **Không lưu plain text**. |
| `full_name` | VARCHAR(100) | Họ tên đầy đủ của khách hàng. |
| `phone` | VARCHAR(20) | Số điện thoại liên hệ. |
| `status` | VARCHAR(20) | Trạng thái tài khoản: `ACTIVE`, `INACTIVE`, `LOCKED`, `PENDING`. |
| `created_at` | TIMESTAMP | Thời điểm tạo bản ghi. |
| `updated_at` | TIMESTAMP | Thời điểm cập nhật lần cuối. |

### Quan hệ
- **1 user** có thể có **nhiều accounts** (1-N).

### Ghi chú
- Dùng `password_hash` thay vì `password` để tuân thủ Rule 63 (mã hóa mật khẩu).
- `status` giúp khóa tài khoản hoặc chờ xác minh.

---

## 2. Bảng `accounts`

### Nhiệm vụ
Lưu thông tin **tài khoản ngân hàng** của mỗi user. Mỗi user có thể có nhiều tài khoản (ví dụ: tiết kiệm, thanh toán).

### Mô tả chi tiết từng cột

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| `id` | BIGINT | Khóa chính, tự tăng. Định danh nội bộ. |
| `user_id` | BIGINT | Khóa ngoại → `users.id`. Tài khoản thuộc về user nào. |
| `account_number` | VARCHAR(20) | Số tài khoản hiển thị (VD: 1234567890). **Unique** - không trùng. |
| `account_type` | VARCHAR(50) | Loại TK: `SAVINGS`, `CHECKING`, `CURRENT`. |
| `balance` | DECIMAL(18,2) | Số dư hiện tại. Dùng DECIMAL tránh lỗi làm tròn số thực. |
| `currency` | VARCHAR(3) | Mã tiền tệ: `VND`, `USD`. |
| `status` | VARCHAR(20) | Trạng thái: `ACTIVE`, `FROZEN`, `CLOSED`. |
| `created_at` | TIMESTAMP | Thời điểm mở tài khoản. |
| `updated_at` | TIMESTAMP | Thời điểm cập nhật (VD: sau giao dịch). |

### Quan hệ
- Thuộc về **1 user** (N-1).
- Là **nguồn** hoặc **đích** của nhiều **transactions** (1-N).

### Ghi chú
- `account_number` là số hiển thị cho khách hàng; `id` dùng nội bộ.
- `balance` luôn ≥ 0 (có thể thêm CHECK constraint).

---

## 3. Bảng `transactions`

### Nhiệm vụ
Lưu **lịch sử giao dịch**: chuyển tiền, rút tiền, nạp tiền. Mỗi giao dịch ghi nhận tài khoản nguồn, tài khoản đích, số tiền và loại giao dịch.

### Mô tả chi tiết từng cột

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| `id` | BIGINT | Khóa chính, tự tăng. Định danh giao dịch. |
| `from_account_id` | BIGINT | Khóa ngoại → `accounts.id`. Tài khoản **chuyển đi** (nguồn). |
| `to_account_id` | BIGINT | Khóa ngoại → `accounts.id`. Tài khoản **nhận vào** (đích). |
| `amount` | DECIMAL(18,2) | Số tiền giao dịch. Luôn > 0. |
| `type` | VARCHAR(50) | Loại: `TRANSFER`, `DEPOSIT`, `WITHDRAW`. |
| `description` | VARCHAR(255) | Mô tả giao dịch (VD: "Chuyển tiền mua hàng"). |
| `status` | VARCHAR(20) | Trạng thái: `SUCCESS`, `PENDING`, `FAILED`, `CANCELLED`. |
| `created_at` | TIMESTAMP | Thời điểm giao dịch. |

### Quan hệ
- **from_account_id** → `accounts` (N-1).
- **to_account_id** → `accounts` (N-1).

### Ghi chú đặc biệt

| Loại giao dịch | `from_account_id` | `to_account_id` |
|----------------|-------------------|------------------|
| **TRANSFER** (chuyển nội bộ) | TK nguồn | TK đích |
| **DEPOSIT** (nạp tiền) | NULL hoặc TK hệ thống | TK nhận |
| **WITHDRAW** (rút tiền) | TK rút | NULL hoặc TK hệ thống |

Có thể tạo bảng `system_accounts` cho giao dịch nạp/rút, hoặc cho phép NULL tạm thời khi học.

---

## Sơ đồ quan hệ (tóm tắt)

```
users (1) ----< (N) accounts
accounts (1) ----< (N) transactions (from)
accounts (1) ----< (N) transactions (to)
```

---

## Cách xem PlantUML

1. **VS Code**: Cài extension "PlantUML", mở `database.puml` → Alt+D để preview.
2. **Online**: Copy nội dung vào [plantuml.com/plantuml](https://www.plantuml.com/plantuml).
3. **Export PNG**: Dùng extension hoặc CLI `plantuml database.puml`.
