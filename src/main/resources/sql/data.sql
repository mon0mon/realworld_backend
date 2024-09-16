-- 테스트용 유저 정보 추가
INSERT INTO users (email, password)
VALUES ('user1@example.com', '123');

INSERT INTO profile (user_id, username)
VALUES (1, 'user1');