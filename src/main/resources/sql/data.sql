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

-- Article 조회용 정보 추가
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Java EE', 'Java Enterprise Edition',
        'Java Platform, Enterprise Edition (Java EE) is the standard in community-driven enterprise software.',
        random_uuid(), current_timestamp, current_timestamp);

INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Spring', 'Spring Framework',
        'The Spring Framework is an application framework and inversion of control container for the Java platform.',
        random_uuid(), current_timestamp, current_timestamp);

INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Node.js', 'Node.js',
    'Node.js is a cross-platform, open-source JavaScript runtime environment.',
        random_uuid(), current_timestamp, current_timestamp);

INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Next.js', 'The React Framework',
    'Open-source web development framework React-based web applications with server-side rendering and static website generation.',
        random_uuid(), current_timestamp, current_timestamp);

INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Struts', 'Apache Struts',
    'Apache Struts is a free, open-source, MVC framework for creating elegant, modern Java web applications.',
        random_uuid(), current_timestamp, current_timestamp);

INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'ASP.NET', 'ASP.NET',
    'ASP.NET is a server-side web-application framework designed for web development to produce dynamic web pages.',
        random_uuid(), current_timestamp, current_timestamp);