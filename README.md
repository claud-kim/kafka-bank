
# This bank log 시뮬레이션 프로그램


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


## rest 확인 방법
# 전체 확인 
time curl -v http://localhost:4567/users

# 유저별 확인
time curl -v http://localhost:4567/users/0

time curl -v http://localhost:4567/users/1


```


## 설계 

