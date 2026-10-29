-- users : 회원
-- ⚠ 참고용 스크립트입니다. 실제 테이블은 spring.jpa.hibernate.ddl-auto=update 설정에 의해 자동 생성됩니다.
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `no` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '회원번호',
  `id` VARCHAR(36) NOT NULL UNIQUE COMMENT 'UUID',
  `username` VARCHAR(100) NOT NULL UNIQUE COMMENT '아이디',
  `password` VARCHAR(200) NOT NULL COMMENT '비밀번호',
  `name` VARCHAR(100) NOT NULL COMMENT '이름',
  `email` VARCHAR(200) DEFAULT NULL COMMENT '이메일',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  `enabled` INT DEFAULT 1 COMMENT '활성화여부'
) COMMENT='회원';