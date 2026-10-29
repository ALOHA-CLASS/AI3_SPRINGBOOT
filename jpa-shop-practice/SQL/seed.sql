-- Active: 1788828726528@@127.0.0.1@3306@jpql
-- ============================================================
-- jpa-shop-practice 샘플 데이터 (MySQL)
-- 실행 전 애플리케이션을 한 번 구동해 ddl-auto=update 로 테이블이
-- 생성된 상태여야 합니다.
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- 예전 스키마에서 남은 orders.user_no 의 잘못된 UNIQUE 제약 제거 (ManyToOne 이라 유니크면 안 됨)
-- SET @idx := (
--     SELECT INDEX_NAME FROM INFORMATION_SCHEMA.STATISTICS
--     WHERE TABLE_SCHEMA = DATABASE()
--       AND TABLE_NAME = 'orders'
--       AND COLUMN_NAME = 'user_no'
--       AND NON_UNIQUE = 0
--       AND INDEX_NAME <> 'PRIMARY'
--     LIMIT 1
-- );
-- SET @sql := IF(@idx IS NULL, 'SELECT 1', CONCAT('ALTER TABLE orders DROP INDEX `', @idx, '`'));
-- PREPARE stmt FROM @sql;
-- EXECUTE stmt;
-- DEALLOCATE PREPARE stmt;

TRUNCATE TABLE order_item;
TRUNCATE TABLE orders;
TRUNCATE TABLE cart_item;
TRUNCATE TABLE cart;
TRUNCATE TABLE product;
TRUNCATE TABLE users;

-- ------------------------------------------------------------
-- users (no: 1~5)
-- ------------------------------------------------------------
INSERT INTO users (id, username, email) VALUES
(UUID(), 'kim01', 'kim01@test.com'),
(UUID(), 'lee02', 'lee02@test.com'),
(UUID(), 'park03', 'park03@test.com'),
(UUID(), 'choi04', 'choi04@test.com'),
(UUID(), 'jung05', 'jung05@test.com');

-- ------------------------------------------------------------
-- product (no: 1~8)
-- ------------------------------------------------------------
INSERT INTO product (id, name, price, stock) VALUES
(UUID(), '무선 마우스', 15000, 100),
(UUID(), '기계식 키보드', 89000, 50),
(UUID(), '27인치 모니터', 259000, 30),
(UUID(), '노트북 거치대', 32000, 80),
(UUID(), 'USB-C 허브', 45000, 60),
(UUID(), '웹캠', 68000, 40),
(UUID(), '헤드셋', 55000, 70),
(UUID(), '게이밍 의자', 189000, 20);

-- ------------------------------------------------------------
-- cart : 사용자당 1개 (no: 1~5, user_no 컬럼은 users.no 를 참조)
-- ------------------------------------------------------------
INSERT INTO cart (id, user_no) VALUES
(UUID(), 1),
(UUID(), 2),
(UUID(), 3),
(UUID(), 4),
(UUID(), 5);

-- ------------------------------------------------------------
-- cart_item (cart_no / product_no 컬럼은 각각 cart.no / product.no 를 참조)
-- ------------------------------------------------------------
INSERT INTO cart_item (id, cart_no, product_no, quantity) VALUES
(UUID(), 1, 1, 2),
(UUID(), 1, 2, 1),
(UUID(), 2, 3, 1),
(UUID(), 3, 4, 3),
(UUID(), 3, 5, 2),
(UUID(), 4, 6, 1),
(UUID(), 5, 7, 2),
(UUID(), 5, 8, 1);

-- ------------------------------------------------------------
-- orders (no: 1~6, user_no 컬럼은 users.no 를 참조)
-- ------------------------------------------------------------
INSERT INTO orders (id, user_no, order_date, status) VALUES
(UUID(), 1, '2026-08-01 10:00:00', 'COMPLETE'),
(UUID(), 1, '2026-08-15 14:30:00', 'PAID'),
(UUID(), 2, '2026-08-20 09:15:00', 'SHIPPING'),
(UUID(), 3, '2026-08-25 16:45:00', 'READY'),
(UUID(), 4, '2026-09-01 11:20:00', 'CANCEL'),
(UUID(), 5, '2026-09-05 13:10:00', 'COMPLETE');

-- ------------------------------------------------------------
-- order_item (order_no / product_no 컬럼은 각각 orders.no / product.no 를 참조)
-- ------------------------------------------------------------
INSERT INTO order_item (id, order_no, product_no, quantity, price) VALUES
(UUID(), 1, 1, 1, 15000),
(UUID(), 1, 3, 1, 259000),
(UUID(), 2, 2, 2, 89000),
(UUID(), 3, 5, 1, 45000),
(UUID(), 4, 6, 1, 68000),
(UUID(), 4, 7, 1, 55000),
(UUID(), 5, 8, 1, 189000),
(UUID(), 6, 4, 2, 32000),
(UUID(), 6, 2, 1, 89000);

SET FOREIGN_KEY_CHECKS = 1;
