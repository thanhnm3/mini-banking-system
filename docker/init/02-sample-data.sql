/*
 * Mini Banking System - Sample Data
 * Author: Mini Banking System Project
 * Description: Dữ liệu mẫu để test và học
 * Password mặc định: "password" (bcrypt hash)
 */

-- Xóa dữ liệu cũ (chạy khi init lần đầu, bảng mới tạo nên trống)
TRUNCATE TABLE transactions, accounts, users RESTART IDENTITY CASCADE;

-- Users (password = "password" - bcrypt hash)
INSERT INTO users (email, password_hash, full_name, phone, status) VALUES
  ('nguyen.van.a@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Nguyễn Văn A', '0901234567', 'ACTIVE'),
  ('tran.thi.b@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Trần Thị B', '0912345678', 'ACTIVE'),
  ('le.van.c@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Lê Văn C', '0923456789', 'ACTIVE');

-- Accounts (user 1: 2 TK, user 2: 1 TK, user 3: 1 TK)
INSERT INTO accounts (user_id, account_number, account_type, balance, currency, status) VALUES
  (1, '1001001001', 'CHECKING', 15000000.00, 'VND', 'ACTIVE'),
  (1, '1001001002', 'SAVINGS', 50000000.00, 'VND', 'ACTIVE'),
  (2, '1002002001', 'CHECKING', 25000000.00, 'VND', 'ACTIVE'),
  (3, '1003003001', 'CHECKING', 8000000.00, 'VND', 'ACTIVE');

-- Transactions (chuyển tiền, nạp tiền giữa các tài khoản)
INSERT INTO transactions (from_account_id, to_account_id, amount, type, description, status) VALUES
  (1, 2, 5000000.00, 'TRANSFER', 'Chuyển tiền tiết kiệm', 'SUCCESS'),
  (2, 3, 3000000.00, 'TRANSFER', 'Chuyển trả nợ', 'SUCCESS'),
  (NULL, 1, 10000000.00, 'DEPOSIT', 'Nạp tiền tại quầy', 'SUCCESS'),
  (1, NULL, 2000000.00, 'WITHDRAW', 'Rút tiền ATM', 'SUCCESS'),
  (3, 4, 1000000.00, 'TRANSFER', 'Chuyển tiền mua hàng', 'SUCCESS');
