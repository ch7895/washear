### transaction

#### transaction isolation 

2개이상의 트랜잭션에서 같은 데이터를 변경하거나 접근할때 안정성과 성능에 따라 취사선택이 가능하나 dirty read가 발생하지 않도록 처리

- read uncommited
- read commited (oracle default)
    * A트랜잭션이 값을 변경하는 동안(트랙잭션이 끝나지 않은 경우) B트랜잭션은 undo에서 조회하므로 이전값 조회 가능, 단 B가 A커밋 이후 다시 조회할 경우 값이 변경되어 있을 수 있음
- repeatable read (mysql default)
    * A트랜잭션이 값을 변경하는 것과 상관없이 B트랜잭션이 선행 되었을 경우 B트랜잭션 이전에 변경된 undo에서 조회
- serializable

 

#### spring @transactional 

##### propagation 

함수내 쿼리 수행이 많을 경우, 함수 실행과정에서 이를 전부 롤백 시킬지 일부만 시킬지
이력관리가 필요한 쿼리의 경우는 가급적 항상 쌓일 수 있도록 처리 (실패/성공을 모두 관리하기 위함)
외부연동 작업이 포함되어있을 경우는 상황에 맞춰서 외부연동 하는 시점을 적절한 곳에 배치

- MANDATORY
    * Support a current transaction, throw an exception if none exists.
- NESTED
    * Execute within a nested transaction if a current transaction exists, behave like REQUIRED otherwise.
- NEVER
    * Execute non-transactionally, throw an exception if a transaction exists.
- NOT_SUPPORTED
    * Execute non-transactionally, suspend the current transaction if one exists.
- REQUIRED
    * Support a current transaction, create a new one if none exists.
- REQUIRES_NEW
    * Create a new transaction, and suspend the current transaction if one exists.
- SUPPORTS
    * Support a current transaction, execute non-transactionally if none exists.

#### 참고
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html
- https://nesoy.github.io/articles/2019-05/Database-Transaction-isolation



