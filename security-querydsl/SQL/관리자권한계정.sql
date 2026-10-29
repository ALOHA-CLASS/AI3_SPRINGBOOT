-- ⚠ 참고용 스크립트입니다. (user_auth 는 users.no 를 FK(user_no)로 참조합니다)
-- 사용자 계정
INSERT INTO `users` (id, username, password, name, email)
VALUES
    (UUID(), 'user', '$2a$10$PMkZ5OE8AxstxMJi1RSHtu.Z4W2m7ZGSPTiruR1navWluw9hk6/Pq',
     '일반 사용자', 'user@naver.com'),

    (UUID(), 'aloha', '$2a$10$PMkZ5OE8AxstxMJi1R1navWluw9hk6/Pq',
     '알로하', 'aloha@naver.com');


-- 사용자 권한
INSERT INTO `user_auth` (id, user_no, auth)
VALUES
    (UUID(), (SELECT no FROM users WHERE username = 'user'), 'ROLE_USER'),
    (UUID(), (SELECT no FROM users WHERE username = 'aloha'), 'ROLE_USER');

-- 관리자 계정
INSERT INTO `users` (id, username, password, name, email)
VALUES (UUID(), 'admin', '$2a$10$PMkZ5OE8AxstxMJi1RSHtu.Z4W2m7ZGSPTiruR1navWluw9hk6/Pq', 
        '관리자', 'admin@naver.com');

INSERT INTO `user_auth` (id, user_no, auth )
VALUES 
  (UUID(), (SELECT no FROM users WHERE username = 'admin'), 'ROLE_USER'),
  (UUID(), (SELECT no FROM users WHERE username = 'admin'), 'ROLE_ADMIN');