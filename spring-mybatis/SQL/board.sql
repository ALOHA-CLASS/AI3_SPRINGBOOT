CREATE TABLE `board` (
  `no` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id` varchar(64) DEFAULT NULL UNIQUE,
  `title` varchar(100) NOT NULL,
  `writer` varchar(100) NOT NULL,
  `content` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP 
                            ON UPDATE CURRENT_TIMESTAMP,
);


TRUNCATE Table board;

INSERT INTO board (id, title, writer, content)
VALUES 
  ( UUID(), '제목1', '작성자1', '내용1'),
  ( UUID(), '제목2', '작성자2', '내용2'),
  ( ㅎ(), '제목3', '작성자3', '내용3'),
  ( UUID(), '제목4', '작성자4', '내용4'),
  ( UUID(), '제목5', '작성자5', '내용5')
;