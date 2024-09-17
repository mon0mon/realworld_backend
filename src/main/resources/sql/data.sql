-- 테스트용 유저 정보 추가
INSERT INTO users (email, password)
VALUES ('user1@example.com', '123');

INSERT INTO profile (user_id, username)
VALUES ((select id from users where email like 'user1@example.com'), 'user1');

-- Profile 조회용 유저 정보 추가
INSERT INTO users (email, password)
VALUES ('celeb@example.com', '123');

INSERT INTO profile (user_id, username, bio, image)
VALUES ((select id from users where email like 'celeb@example.com'), 'celeb', 'Java is Awsome',
        'https://simpleicons.org/icons/openjdk.svg');