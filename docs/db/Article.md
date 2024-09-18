# Article

- [Article](#article)
    - [1. Article List Filtering (Dynamic Query)](#1-article-list-filtering-dynamic-query)

### 1. Article List Filtering (Dynamic Query)
| Article 검색 간에 JPA의 Criteria API를 사용한 이유

- QueryDSL과 같은 외부 라이브러리 사용 시에는 추가적인 설정 및 별도의 구조 구성이 필요
- 별도의 설정 및 기본으로 제공되는 부분을 높이 평가
- Article 이외에는 별도로 동적 쿼리가 필요하지 않기때문에 재사용성을 고려하지 않음

> 빠른 개발 속도 및 기본 제공 요소로 인해 사용

### 2. Article - Favorite FetchType
| Article과 Favorite 연관관계에서 FetchType.Eager를 사용한 이유

- 인증이 된 상태에서 게시글 조회를 할 경우, 해당 게시글에 좋아요 여부를 확인해야함
- 매 게시글 마다 체크하기 때문에, FetchType.Lazy 보다 @EntityGraph와 FetchType.Eager를 사용하는 것이 성능상 좋을 것이라고 판단

> 필수적으로 좋아요 여부를 확인하기 때문에, 초기에 값을 불러오는 Eager와 @EntityGraph 사용