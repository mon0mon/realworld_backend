# Article

- [Article](#article)
    - [1. Article List Filtering (Dynamic Query)](#1-article-list-filtering-dynamic-query)

### 1. Article List Filtering (Dynamic Query)
| Article 검색 간에 JPA의 Criteria API를 사용한 이유

- QueryDSL과 같은 외부 라이브러리 사용 시에는 추가적인 설정 및 별도의 구조 구성이 필요
- 별도의 설정 및 기본으로 제공되는 부분을 높이 평가
- Article 이외에는 별도로 동적 쿼리가 필요하지 않기때문에 재사용성을 고려하지 않음

> 빠른 개발 속도 및 기본 제공 요소로 인해 사용

### 2. Article - Favorite, Article -Tag N+1

- 기본적으로 N:1 또는 N:M인 관계에서 N+1 문제 발생
- Article 엔티티를 조회간에 @EntityGraph를 이용해서 조인해서 데이터를 조회하도록 수정

### 3. Fetch Join과 Pagination 오류
> Fetch Join과 Pagaination을 같이 사용할 경우 다음과 같은 오류 문구 발생
> **HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory**

- 메모리 누수를 방지하기 위해서는 BatchSize나 쿼리 분리 등의 방법이 존재
- 현 프로젝트에서는 쿼리 분리 작업으로 문제 해결
- 게시글 목록 조회 간 Pagination Query와, Entity Join Query가 별도로 나가도록 설정
  1. ArticleService에서 생성된 Specification을 @EntityGraph가 적용되지 않은 JPA Method로 실행
  2. 리턴되는 Page<Article> 객체에서 Article Id만 추출
  3. @EntityGraph가 적용되는 JPA Method로 Article Id List를 실행
  4. 리턴되는 값을 Page<Article>에 담아 리턴
