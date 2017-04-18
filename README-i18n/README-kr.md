# 언제 어디서나 Redis서버를 빠르고 쉽게 모니터링 하세요. 다양한 분석 기능을 통해 서버의 성능생애주기를 진단하십시오.
성능생애주기 진단 및 예측 기능을 통해 미리 서버 장애를 예방하여 안정되고 최적화된 서비스를 운영할 수 있습니다.
Redis 모니터링 앱은 팡데이터(Pangdata.com)를 사용하여 개발 되었습니다. 모든 저장, 분석, 모니터링 기술은 Pangdata 클라우드 서비스가 제공하며 팡 앱만 여러분의 서버에 설치하시면 됩니다.

# 클라우드기반 모니터링, 분석 서비스 
팡데이터는 SaaS(Software as a Service)로써 빠르고 편리하며 강력한 분석 기술을 통해 여러분이 인프라스트럭처(Infrastructure)를 가치있게 합니다.
또한 <a href="https://github.com/pangdata/pang-sdk-java">Pang SDK</a>를 사용하여 여러분이 원하는 인프라스트럭처(Infrastructure)를 모니터링 및 분석 할 수 있습니다.

## 스크린 샷
###### 실시간 모니터링 대시보드 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard.PNG" width="600" />

###### 모바일에서의 실시간 대시보드(데스크톱과 동일하게 모든 기능 사용가능) ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard-mobile.jpg" width="300" />

###### 등록된 레디스 모니터링 항목 리스트 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/devices.PNG" width="300" />

###### CPU 통계분석 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-cpu.PNG" width="300" />

###### Memory 통계분석 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-memory.PNG" width="300" />

###### Stats 통계분석 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-stats.PNG" width="300" />

## 시작하기
#### Pangdata.com 가입하기 ####
Redis 팡앱 설치에 앞서 먼저 팡데이터에 가입해야 합니다. 이메일 주소만 있으면 가입이 가능합니다.
팡데이터로 가셔서 <a href="http://pangdata.com" target="_blank">https://pangdata.com</a> 가입하시고 프로파일에 있는 사용자 키를 사용하여 팡 앱을 설정해야 합니다.

#### 이 팡앱이 수행되기 위해서는 아래 프로그램이 설치 되어 있어야 합니다 ####
최소 **Java 1.5+** 이상이 설치 되어 있어야 합니다.

#### 빠르고 편리한 설치 ####
5분 안에 설치할 수 있습니다^^.

##### 단계 1 #####

###### Windows ######
최신 릴리즈 리스트에서 <a href="https://github.com/pang-apps/redis-monitor/releases/latest">Redis Server 모니터링 어플리케이션</a> 다운로드하여 설치하고자 하는 경로에 압축파일을 푸세요.

###### Linux ######
``` 
wget https://github.com/pang-apps/redis-monitor/releases/download/[version]/redis-monitor.tar
tar -xvf redis-monitor.tar
``` 
##### Step 2: pang.properties 파일 설정하기 #####
redis-monitor/conf/pang.properties 파일을 노트패드나 vi(Linux)로 수정합니다.

###### Step 2-1: 사용자ID와 사용자 키를 설정합니다 ######
```bash
pang.username=팡데이터 사용자 ID
pang.userkey=발급된 사용자 키
``` 
Note: 사용자 키는 로그인후 프로파일에서 확인할 수 있습니다.

###### Step 2-2: 모니터링 대상 Redis Server 설정하기 ######
Note: prefix 에는 공백을 사용할 수 없습니다.
```bash
redis.1.prefix = my_redis1
redis.1.host = address:6379
#redis.1.auth = password

#redis.2.prefix = my_redis2
#redis.2.host = address2:6379
#redis.2.auth = password
``` 
Note: 복수개의 서버를 설정할 경우 index를 추가해서 사용할 수 있습니다('redis.[index]').

###### Step 2-3: Sentinel 을 구성하여 고 가용성 레디스를 사용하는 경우 ######
```bash
redis.1.prefix = my_redis1
redis.1.host = ip:26379,ip:26380
#redis.1.auth = password
redis.1.master = mastername
``` 

###### Step 2-4: Redis 모니터링 항목을 설정하세요 ######
```
## Stats
monitor.instantaneous_input_kbps = true
monitor.instantaneous_output_kbps = true
monitor.instantaneous_ops_per_sec = true
monitor.total_connections_received = false
monitor.total_commands_processed = false
monitor.total_net_input_bytes = false
monitor.rejected_connections = false

## CPU
monitor.used_cpu_sys = true
monitor.used_cpu_user = true

## Memory
monitor.used_memory = true
monitor.used_memory_rss = true
monitor.used_memory_peak = true
monitor.mem_fragmentation_ratio = false
monitor.used_memory_lua = false

## Client
monitor.connected_clients = true

## Keyspace
monitor.keys = true
monitor.expires = false
monitor.avg_ttl = false
```
그 외의 모니터링 항목을 찾으시려면 redis.io를 방문하여 확인하세요. 아래는 Section별 모니터링 상세 항목입니다.
https://redis.io/commands/info

##### Step 3: 실행 #####
###### Windows ######
``` 
redis-monitor/pang.bat
``` 
###### Linux ######
백그라운드 모드로 수행됩니다.
``` 
redis-monitor/pang.sh
``` 
레디스 팡앱 로그 확인. 설정 문제로 인해 구동 시 에러가 발생할 수 있습니다. 반드시 로그를 확인하세요.
로그는 logs/pang.log 파일에 있습니다.
``` 
redis-monitor/log.sh
``` 
##### Step 4: 여러분의 데이터를 확인해 보세요 #####
Pangdata.com 로그인후 디바이스 리스트에 등록된 모니터링 항목을 확인하세요.

대시보드를 생성하여 다양한 위젯을 사용하여 실시간 모니터링을 사용하세요. 

##### Step 5: 자 이제 여러분의 데이터를 즐길 시간입니다 #####
모든 설치가 끝났습니다. 여러분의 데이터를 즐기고 새로운 가치를 찾아보세요. 
하루 이틀, 일주일, 한달 시간이 지나면서 여러분의 서비스가 어떻게 성장하고 있는 통계분석을 통해 진단하십시오.

##### 데모를 사용해 보세요 #####
https://pangdata.com 접속하셔서 사용자 ID에  'pang-demo' 와 패스워드에  'panggood' 입력해서 로그인하십시오.
