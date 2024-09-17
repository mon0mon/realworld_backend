## API Specs

### Auth

- 이용자 회원가입
- 이용자 로그인
- 이용자 로그인 (rememberMe)
- 현재 로그인한 이용자
- 이용자 정보 변경

### Profile

- 이용자 프로파일 조회
- 이용자 팔로우
- 이용자 팔로우 삭제

### Articles

- 단일 게시글 조회
  - /articles/slug

- 모든 게시글 조회
  - 작성자 필터링 (author)
  - pagination, limit
  - 태그 필터링 (tag)
  - 특정 유저가 좋아요를 누른 게시글 (favorited)

### Articles, Favorite, Comments

- 게시글 등록
- 피드
  - 이용자 Followee가 작성한 게시글