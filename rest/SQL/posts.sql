-- posts (게시글) 테이블
DROP TABLE IF EXISTS `posts`;
CREATE Table `posts` (
    `no`            BIGINT          AUTO_INCREMENT PRIMARY KEY  COMMENT 'PK',
    `id`            VARCHAR(64)     UNIQUE                      COMMENT 'UK',
    `title`         VARCHAR(100)    NOT NULL                    COMMENT '제목',
    `writer`        VARCHAR(100)    NOT NULL                    COMMENT '작성자',
    `content`       TEXT            NULL                        COMMENT '내용',
    `created_at`    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '등록일자',
    `updated_at`    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   
                                            ON UPDATE CURRENT_TIMESTAMP    
                                                                COMMENT '수정일자'
) COMMENT '게시글';