-- user_auth : 회원권한
-- ⚠ 참고용 스크립트입니다. 실제 테이블은 spring.jpa.hibernate.ddl-auto=update 설정에 의해 자동 생성됩니다.
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `no` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '권한번호',
  `id` VARCHAR(36) NOT NULL UNIQUE COMMENT 'UUID',
  `user_no` BIGINT NOT NULL COMMENT '회원번호(FK)',
  `auth` VARCHAR(100) NOT NULL COMMENT '권한 (ROLE_USER, ROLE_ADMIN, ...)',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  FOREIGN KEY (user_no) REFERENCES `users`(no)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) COMMENT='회원권한';