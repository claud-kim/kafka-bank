
# This is bank log 시뮬레이션 프로그램



## maven dependency
### v2
![maven dependency](/images/v2_mvn_dep.png)

### v1
![maven dependency](/images/mvn_dep.png)

## 빌드 방법
```bash
# clean & test & package .
mvn clean package


```


## 실행 방법 : 터미널 두 개 필요
```bash

## 시작 방법
# 터미널 1 : 컨슈머 실행
./start-test-consumer.sh

# 터미널 2: 프로듀서 실행
./start-test-producer.sh



## 종료 방법
# 터머널 2: 프로듀서 종료
[ctrl+c] | kill [pid] 

# 터미널 1: 컨슈머 종료 
[ctrl+c] | kill [pid] 


## 확인 방법
터미널 1, 2  
===== / ==== 값을 비교하여 같은 지 확인.
./start-test-check.sh 


## rest 확인 방법
# 전체 확인 
time curl -v http://localhost:4567/users

# 유저별 확인
time curl -v http://localhost:4567/users/0

time curl -v http://localhost:4567/users/1


```


## 테스트 결과
### v2 테스트 결과
* 프로듀서 종료시 로그
!["프로듀서 구동시 로그"](/images/v2_p1.png)
* 컨슈머 종료시 로그
!["컨슈머 구동시 로그"](/images/v2_c1.png)
* 프로듀서 구동시 로그
!["프로듀서 종료시 로그"](/images/v2_p2.png) 
* 컨슈머 구동시 로그
!["컨슈머 종료시 로그"](/images/v2_c2.png)
* REST 조회 전체
!["REST 조회 전체"](/images/v2_r1.png)
* REST 조회 고객번호
!["REST 조회 고객번호"](/images/v2_r2.png)
* 결과 확인 gen vs consumer diff console
!["결과 확인 gen vs consumer diff console"](/images/v2_o1.png)

### v1 테스트 결과
* 프로듀서 구동시 로그
!["프로듀서 구동시 로그"](/images/p1.png)
* 컨슈머 구동시 로그
!["컨슈머 구동시 로그"](/images/c1.png)
* 프로듀서 종료시 로그
!["프로듀서 종료시 로그"](/images/p2.png) 
* 컨슈머 종료시 로그
!["컨슈머 종료시 로그"](/images/c2.png)
* REST 조회 전체
!["REST 조회 전체"](/images/r1.png)
* REST 조회 고객번호
!["REST 조회 고객번호"](/images/r2.png)
* 결과 확인 gen vs consumer diff console
!["결과 확인 gen vs consumer diff console"](/images/d1.png)
* 결과 확인 gen vs consumer diff shell
!["결과 확인 gen vs consumer diff shell"](/images/d2.png)


## 설계 

TODO
