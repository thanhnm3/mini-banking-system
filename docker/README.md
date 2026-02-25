# Docker - Mini Banking System

## Chạy PostgreSQL

```bash
# Từ thư mục gốc project
docker-compose up -d

# Xem log
docker-compose logs -f postgres
```

## Thông tin kết nối

| Thông tin | Giá trị |
|-----------|---------|
| Host | localhost |
| Port | 5432 |
| Database | minibank |
| User | minibank |
| Password | minibank123 |
| JDBC URL | `jdbc:postgresql://localhost:5432/minibank` |

## Dữ liệu mẫu

- **3 users**: nguyen.van.a@email.com, tran.thi.b@email.com, le.van.c@email.com
- **Password**: `password` (cho tất cả)
- **4 tài khoản** với số dư mẫu
- **5 giao dịch** (chuyển, nạp, rút)

## Lệnh hữu ích

```bash
# Dừng container
docker-compose down

# Xóa cả volume (reset DB)
docker-compose down -v
```
