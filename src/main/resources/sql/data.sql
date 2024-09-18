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
-- 1
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Java EE', 'Java Enterprise Edition',
        'Java Platform, Enterprise Edition (Java EE) is the standard in community-driven enterprise software.',
        'java-ee', current_timestamp, current_timestamp);

-- 2
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Spring', 'Spring Framework',
        'The Spring Framework is an application framework and inversion of control container for the Java platform.',
        'spring', current_timestamp, current_timestamp);

-- 3
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Node.js', 'Node.js',
    'Node.js is a cross-platform, open-source JavaScript runtime environment.',
        'node-js', current_timestamp, current_timestamp);

-- 4
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Next.js', 'The React Framework',
    'Open-source web development framework React-based web applications with server-side rendering and static website generation.',
        'next-js', current_timestamp, current_timestamp);

-- 5
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'Struts', 'Apache Struts',
    'Apache Struts is a free, open-source, MVC framework for creating elegant, modern Java web applications.',
        'apache-struts', current_timestamp, current_timestamp);

-- 6
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'celeb@example.com'), 'ASP.NET', 'ASP.NET',
    'ASP.NET is a server-side web-application framework designed for web development to produce dynamic web pages.',
        'asp-net', current_timestamp, current_timestamp);

-- 1
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'user1@example.com'), 'C', 'C',
        'C is a high-level, general-purpose programming language',
        'c', current_timestamp, current_timestamp);

-- 2
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'user1@example.com'), 'C++', 'CPP',
        'C++ is a high-level, general-purpose programming language',
        'cpp', current_timestamp, current_timestamp);

-- 3
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'user1@example.com'), 'C#', 'C Sharp',
        'C# is a general-purpose high-level programming language supporting multiple paradigms.',
        'c-', current_timestamp, current_timestamp);

-- 4
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'user1@example.com'), 'Java', 'Java',
        'Java is a high-level, class-based, object-oriented programming language',
        'java', current_timestamp, current_timestamp);

-- 5
INSERT INTO article (author_id, title, description, body, slug, created_at, updated_at)
values ((select id from users where email like 'user1@example.com'), 'JavaScript', 'js',
        'JavaScript is a programming language and core technology of the Web',
        'javascript', current_timestamp, current_timestamp);