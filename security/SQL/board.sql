-- board : 게시글
DROP TABLE IF EXISTS `board`;
CREATE Table `board` (
    `no`            BIGINT          AUTO_INCREMENT PRIMARY KEY  COMMENT 'PK',
    `id`            VARCHAR(64)     UNIQUE                      COMMENT 'UK',
    `title`         VARCHAR(100)    NOT NULL                    COMMENT '제목',
    -- `writer`        VARCHAR(100)    NOT NULL                    COMMENT '작성자',
    `user_no`       BIGINT          NOT NULL                    COMMENT '회원번호(PK)',
    `content`       TEXT            NULL                        COMMENT '내용',
    `created_at`    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '등록일자',
    `updated_at`    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   
                                            ON UPDATE CURRENT_TIMESTAMP    
                                                                COMMENT '수정일자',
    FOREIGN KEY (user_no) REFERENCES `user`(no)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) COMMENT '게시글';