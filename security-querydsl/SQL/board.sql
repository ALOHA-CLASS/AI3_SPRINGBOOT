-- board : 게시글
-- ⚠ 참고용 스크립트입니다. 실제 테이블은 spring.jpa.hibernate.ddl-auto=update 설정에 의해 자동 생성됩니다.
DROP TABLE IF EXISTS `board`;
CREATE Table `board` (
    `no`            BIGINT          AUTO_INCREMENT PRIMARY KEY  COMMENT 'PK',
    `id`            VARCHAR(36)     UNIQUE                      COMMENT 'UUID',
    `title`         VARCHAR(100)    NOT NULL                    COMMENT '제목',
    `user_no`       BIGINT          NOT NULL                    COMMENT '회원번호(FK)',
    `content`       TEXT            NULL                        COMMENT '내용',
    `created_at`    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '등록일자',
    `updated_at`    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   
                                            ON UPDATE CURRENT_TIMESTAMP    
                                                                COMMENT '수정일자',
    FOREIGN KEY (user_no) REFERENCES `users`(no)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) COMMENT '게시글';