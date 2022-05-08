### transaction



#### transaction isolation 

- read uncommited
- read commited (oracle default)
    * A트랜잭션이 값을 변경하는 동안(트랙잭션이 끝나지 않은 경우) B트랜잭션은 undo에서 조회하므로 이전값 조회 가능, 단 B가 A커밋 이후 다시 조회할 경우 값이 변경되어 있을 수 있음
- repeatable read (mysql default)
    * A트랜잭션이 값을 변경하는 것과 상관없이 B트랜잭션이 선행 되었을 경우 B트랜잭션 이전에 변경된 undo에서 조회
- serializable



#### 참고
-  https://nesoy.github.io/articles/2019-05/Database-Transaction-isolation


