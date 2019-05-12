# log-gen and kafka and consumer-profiler



평가 항목

1. 객체지향 프로그래밍에 대한 이해 

2. 동시성/병렬 프로그래밍에 대한 이해 

3. Kafka 사용법에 대한 이해 

4. Maven 사용법에 대한 이해 

5. Unit Test 작성에 대한 이해 

6. Clean Code에 대한 이해 

과제 설명 

1. 고객의 금융거래 로그를 이용해 고객별 프로파일을 생성하고 조회하고자 한다. 

2. 금융거래 로그는 세션시작, 가입, 계좌개설, 입금, 출금, 이체라는 6가지 유형이 있다. 

　 a. 세션시작 로그 -> SessionStartLog

　　　i. 고객번호(가입시에는 세션 시작로그에 고객번호가 없음) -> userNumber

　　　ii. 세션 시각 -> time

　 b. 가입 로그 -> RegisterLog

　　　i. 고객번호 -> userNumber

　　　ii. 고객명 -> userName

　　　iii. 생년월일 -> birthDay

　　　iv. 가입 시각 -> time

　 c. 계좌개설 로그 -> AccountOpenLog

　　　i. 고객번호 -> userNumber

　　　ii. 계좌번호 -> accountNumber

　　　iii. 계좌개설 시각 -> time

　 d. 입금 로그 -> DepositLog

　　　i. 고객번호 -> userNumber

　　　ii. 입금 계좌번호 -> accountNumber

　　　iii. 입금 금액 -> insertMoney

　　　iv. 입금 시각 -> time

　 e. 출금 로그 -> WithdrawLog

　　　i. 고객번호 -> userNumber

　　　ii. 출금 계좌번호 -> accountNumber

　　　iii. 출금 금액 -> outputMoney

　　　iv. 출금 시각 -> time

　 f. 이체 로그 -> TransferLog

　　　i. 고객번호 -> userNumber

　　　ii. 송금 계좌번호 -> accountNumber

　　　iii. 수취 은행 -> destBank

　　　iv. 수취 계좌번호 -> destAccountNumber

　　　v. 수취 계좌주 -> destUserName

　　　vi. 이체 금액 -> outputMoney

　　　vii. 이체 시각 -> time

3. 금융거래 로그는 JSON 포맷으로 저장되며 Kafka를 통해 전송된다. 


요구사항 
1. Maven 기반으로 프로젝트 구성 

　 a. Maven에서 제공하는 기능을 최대한 활용할 수 있도록 POM 파일 구성 

2. 금융거래 로그를 생성하는 Test Data Generator 프로그램을 구현 

　 a. 평가항목 

　　　i. Kafka Producer를 올바르게 사용했는지 여부 

　　　ii. 다수의 고객을 병렬로 시뮬레이션할 수 있는 아키텍처인지 여부 

　　　iii. OOP 원칙에 맞게 객체 모델링을 했는지 여부 

　 b. 요구사항 

　　　i. 고객은 1번 ~ 50,000번까지 존재해야 한다. 

　　　ii. 고객의 행동을 시뮬레이션해야 한다. 

　　　　　1. 고객의 모든 세션은 (세션시작)부터 시작한다. 

　　　　　2. 고객의 가장 처음 세션은 (가입 → 계좌개설) 순서로 거래가 진행된다. 

　　　　　　　a. 예) 세션시작 → 가입 → 계좌개설 

　　　　　3. 두 번째 세션부터는 (입금, 출금, 이체) 중 하나의 거래가 진행된다. 

　　　　　　　a. 예) 세션시작 → (입금 or 출금 or 이체) 

　　　　　4. 고객의 계좌 잔액은 0원 미만이 될수 없다. 

　　　　　5. 각 고객별로 거래 사이에 랜덤한 시간 간격이 존재한다. 

　　　　　6. 고객의 거래를 이벤트화해 각각의 거래를 Kafka로 즉시 전송한다.

　　　iii. 고객의 동시 접속을 시뮬레이션해야 한다. 

　　　　　1. 동일 시간에 100명의 고객이 거래를 진행하고 있어야 한다. 

　　　　　2. 동일 고객이 동시에 2개 이상의 세션을 가질수 없다. 

　　　iv. 모든 고객이 무한하게 거래를 반복한다. 

3. 금융거래 로그를 기반으로 고객을 프로파일링하는 Profiler 프로그램을 구현 

　 a. 평가항목 

　　　i. Kafka Consumer를 올바르게 사용했는지 여부 

　　　ii. 동시성 프로그래밍 기반으로 효율적으로 고객 프로파일 데이터를 생성했는지 여부 

　　　iii. OOP 원칙에 맞게 객체 모델링을 했는지 여부 

　 b. 요구사항 

　　　i. 메모리 기반 자료구조에 다음과 같은 데이터를 저장한다. 

　　　　　1. 고객 -> Customer

　　　　　　　a. 고객명 -> customerNumber

　　　　　　　b. 생년월일 -> birthDay

　　　　　　　c. 가입일시 -> registerTime

　　　　　　　d. 누적 세션 횟수 -> totalSessionCnt

　　　　　2. 계좌 -> Account

　　　　　　　a. 잔액 -> balance

　　　　　　　b. 거래유형(입금, 출금, 이체)별 최소, 최대 거래금액 
                -> depositLog. min, max
                -> withdrawLog. min, max
                -> transferLog. min, max

　　　　　　　c. 거래유형 구분없이 최근 3건의 거래내역 -> latestTrade list[3]

　　　ii. 특정 고객의 데이터를 JSON 포맷으로 조회 가능한 명령을 REST API로 제공한다. 

　　　iii. 성능을 최적화한 아키텍처로 설계한다. 

　　　　　1. 실시간성 (이벤트 발생시각으로 부터 얼마나 빨리 프로파일 되는지) 

　　　　　2. 프로파일 처리량 

　　　　　3. CPU 부하 

　　　　　4. 메모리 사용량 

4. JUnit, Mockito 등의 테스트 프레임워크를 사용해 클래스 단위 Unit Test 생성 

5. Clean Code에 대한 요구사항 준수 


제약사항 

1. JDK8 이상, 언어는 Java 사용  

2. 외부 라이브러리는 Kafka Client 2.1 이상, JSON 라이브러리, 테스트 프레임워크(Junit, Mockito 등)만 사용 가능 

3. REST API 제공을 위해 SparkJava(http://sparkjava.com) 사용 

4. 그 외 불필요한 서드파티 프레임워크/라이브러리 사용 금지 (예: Spring, Guava, Lombok 등)


제출방법 및 제출처


제출 마감일
