# JPA Shop Practice

Thymeleaf + Bootstrap + Spring Data JPA + JPQL 실습용 프로젝트입니다.

## 환경
- Java 17
- Spring Boot 4.1.1
- Gradle
- MySQL
- Thymeleaf
- Bootstrap 5.3.3 CDN

## DB
```sql
CREATE DATABASE jpa_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

`application.properties`에서 MySQL 계정/비밀번호를 수정하세요.

## 실행
```bash
./gradlew bootRun
```

Windows:
```powershell
gradlew.bat bootRun
```

접속: http://localhost:8080

## 화면
- `/` : 홈
- `/users` : 회원 목록 + JPQL 검색
- `/products` : 상품 목록 + JPQL 검색
- `/carts/{userId}` : 회원 장바구니
- `/orders/{userId}` : 회원 주문내역

## 엔터티
Users → Cart → CartItem → Product
Users → Orders → OrderItem → Product

## JPQL 실습 포인트
- 기본 SELECT
- WHERE / LIKE
- 연관관계 탐색
- JOIN
- JOIN FETCH
- GROUP BY / COUNT
- SUM

## 계층 구조

Controller → Service → Repository → JPA

회원 기능은 다음 형태로 분리되어 있습니다.

- `UserService` : 회원 서비스 인터페이스
- `UserServiceImpl` : 회원 서비스 구현 클래스
- `UsersRepository` : JPQL 및 데이터 접근
- `HomeController` : `UserService`를 주입받아 사용

## Service 계층

각 엔터티별로 `Service 인터페이스 → ServiceImpl → Repository` 구조를 적용했습니다.

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
JPA / DB
```

### 서비스 목록

- `UserService` / `UserServiceImpl`
- `ProductService` / `ProductServiceImpl`
- `CartService` / `CartServiceImpl`
- `CartItemService` / `CartItemServiceImpl`
- `OrderService` / `OrderServiceImpl`
- `OrderItemService` / `OrderItemServiceImpl`

`HomeController`도 Repository를 직접 호출하지 않고 Service 계층을 통해 데이터를 처리하도록 수정했습니다.
