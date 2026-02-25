/*
 * Mini Banking System - Database Schema
 * Author: Mini Banking System Project
 * Description: Create tables for users, accounts, transactions
 * Use Tables: users, accounts, transactions
 * Transaction: No
 */

-- Bảng users: lưu thông tin người dùng (khách hàng)
CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(100),
  phone VARCHAR(20),
  status VARCHAR(20) DEFAULT 'ACTIVE',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng accounts: lưu tài khoản ngân hàng của mỗi user
CREATE TABLE IF NOT EXISTS accounts (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  account_number VARCHAR(20) NOT NULL UNIQUE,
  account_type VARCHAR(50) DEFAULT 'CHECKING',
  balance DECIMAL(18, 2) DEFAULT 0 CHECK (balance >= 0),
  currency VARCHAR(3) DEFAULT 'VND',
  status VARCHAR(20) DEFAULT 'ACTIVE',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng transactions: lịch sử giao dịch (chuyển, rút, nạp)
CREATE TABLE IF NOT EXISTS transactions (
  id BIGSERIAL PRIMARY KEY,
  from_account_id BIGINT REFERENCES accounts(id) ON DELETE SET NULL,
  to_account_id BIGINT REFERENCES accounts(id) ON DELETE SET NULL,
  amount DECIMAL(18, 2) NOT NULL CHECK (amount > 0),
  type VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  status VARCHAR(20) DEFAULT 'SUCCESS',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index cho truy vấn thường dùng
CREATE INDEX IF NOT EXISTS idx_accounts_user_id ON accounts(user_id);
CREATE INDEX IF NOT EXISTS idx_transactions_from_account ON transactions(from_account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_to_account ON transactions(to_account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_created_at ON transactions(created_at);
