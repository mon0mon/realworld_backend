# Article

- [Article](#article)
    - [1. Article List Filtering (Dynamic Query)](#1-article-list-filtering-dynamic-query)

### 1. Article List Filtering (Dynamic Query)
| Article 검색 간에 JPA의 Criteria API를 사용한 이유

- QueryDSL과 같은 외부 라이브러리 사용 시에는 추가적인 설정 및 별도의 구조 구성이 필요
- 별도의 설정 및 기본으로 제공되는 부분을 높이 평가
- Article 이외에는 별도로 동적 쿼리가 필요하지 않기때문에 재사용성을 고려하지 않음

> 빠른 개발 속도 및 기본 제공 요소로 인해 사용