# User

- [User](#user)
    - [1. User-Followee Entity Mapping](#1-user-followee-entity-mapping)

### 1. User-Followee Entity Mapping
| User-Followee Entity 매핑을 @JoinTable로 진행한 이유

- User Entity에서 매번 Followee에 대한 데이터를 즉시(Eager)로 불러올 필요는 없음
- 따라서 해당 값은 FetchType.Lazy로 두고, 필요할 경우에만 조회하도록 하는 것이 성능상 좋을 것이라고 판단
- FetchType.Lazy가 가능한 @JoinTable로 진행