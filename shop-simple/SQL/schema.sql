-- ============================================================
-- Shop
-- MySQL 8.x / DB: aloha
-- ============================================================

-- 회원
CREATE TABLE IF NOT EXISTS users (
    no          BIGINT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(36) NOT NULL UNIQUE,
    username    VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    name        VARCHAR(50) NOT NULL,
    email       VARCHAR(100),
    phone       VARCHAR(30),
    enabled     BIT NOT NULL DEFAULT 1,
    created_at  DATETIME,
    updated_at  DATETIME
);

-- 회원 권한
CREATE TABLE IF NOT EXISTS user_auth (
    no          BIGINT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(36) NOT NULL UNIQUE,
    username    VARCHAR(50) NOT NULL,
    auth        VARCHAR(30) NOT NULL,
    user_no     BIGINT,
    FOREIGN KEY (user_no) REFERENCES users(no)
);

-- 배송지
CREATE TABLE IF NOT EXISTS address (
    no              BIGINT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(36) NOT NULL UNIQUE,
    user_no         BIGINT NOT NULL,
    receiver        VARCHAR(50) NOT NULL,
    phone           VARCHAR(30),
    zipcode         VARCHAR(10),
    address1        VARCHAR(200),
    address2        VARCHAR(200),
    default_address BIT NOT NULL DEFAULT 0,
    created_at      DATETIME,
    updated_at      DATETIME,
    FOREIGN KEY (user_no) REFERENCES users(no)
);

-- 상품
CREATE TABLE IF NOT EXISTS product (
    no          BIGINT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(36) NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    category    VARCHAR(50),
    price       INT NOT NULL,
    stock       INT NOT NULL DEFAULT 0,
    description VARCHAR(1000),
    image_url   VARCHAR(500),
    created_at  DATETIME,
    updated_at  DATETIME
);

-- 장바구니 항목
CREATE TABLE IF NOT EXISTS cart_item (
    no          BIGINT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(36) NOT NULL UNIQUE,
    user_no     BIGINT NOT NULL,
    product_no  BIGINT NOT NULL,
    quantity    INT NOT NULL DEFAULT 1,
    created_at  DATETIME,
    updated_at  DATETIME,
    FOREIGN KEY (user_no) REFERENCES users(no),
    FOREIGN KEY (product_no) REFERENCES product(no)
);

-- 주문
CREATE TABLE IF NOT EXISTS orders (
    no           BIGINT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(36) NOT NULL UNIQUE,
    order_no     VARCHAR(36) NOT NULL UNIQUE,
    user_no      BIGINT NOT NULL,
    total_amount INT NOT NULL,
    status       VARCHAR(20) NOT NULL,
    receiver     VARCHAR(50),
    phone        VARCHAR(30),
    zipcode      VARCHAR(10),
    address1     VARCHAR(200),
    address2     VARCHAR(200),
    created_at   DATETIME,
    updated_at   DATETIME,
    FOREIGN KEY (user_no) REFERENCES users(no)
);

-- 주문항목
CREATE TABLE IF NOT EXISTS order_item (
    no           BIGINT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(36) NOT NULL UNIQUE,
    order_no     BIGINT NOT NULL,
    product_no   BIGINT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    price        INT NOT NULL,
    quantity     INT NOT NULL,
    FOREIGN KEY (order_no) REFERENCES orders(no),
    FOREIGN KEY (product_no) REFERENCES product(no)
);
