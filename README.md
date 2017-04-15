# Translations
- [Korean](https://github.com/pangdata/pang-apps/redis-monitor/blob/master/README-i18n/README-kr.md)

# Monitoring your Redis Server on mobile where ever you are.
Redis Server monitoring application for performance and prediction of failure.
This application used Pangdata.com cloud service. Pangdata.com is cloud based monitoring analysis SaaS platform. 
You can monitor your cloud infrstructure and applications using Pang SDK.

## Screen shot
###### Realtime monitoring dashboard ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard.PNG" width="600" />

###### Realtime monitoring on Mobile ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard-mobile.jpg" width="300" />

###### Device list ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/devices.PNG" width="300" />

###### CPU Analytics ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-cpu.PNG" width="300" />

###### Memory Analytics ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-memory.PNG" width="300" />

###### Stats Analytics ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-stats.PNG" width="300" />

## Getting Started
#### Sign up for Pangdata.com ####
Before you begin, you need an Pangdata.com account. 
Please visit <a href="http://pangdata.com" target="_blank">https://pangdata.com</a> and create an account and retrieve your user key in user profile.

#### Minimum requirements ####
To run the application you will need **Java 1.5+**.

#### Installation ####
Very easy to install ^^.

##### Step 1 #####

###### Windows ######
Download a <a href="https://github.com/pang-apps/redis-monitor/releases/latest">Redis Server monitoring application</a> file and unzip it.

###### Linux ######
``` 
wget https://github.com/pang-apps/redis-monitor/releases/download/[version]/redis-monitor.tar
tar -xvf redis-monitor.tar
``` 
##### Step 2: Configure pang.properties file #####
cd redis-monitor/conf

###### Step 2-1: Confgiure your account and user key in pang.properties ######
```bash
pang.username=your username in pangdata.com
pang.userkey=your user key in pangdata.com
``` 
Note: User key can be found in your profile of Pangdata.com

###### Step 2-2: Confgiure target Redis Server ######
Note: prefix could not use space.
```bash
redis.1.prefix = my_redis1
redis.1.host = address:6379
#redis.1.auth = password

#redis.2.prefix = my_redis2
#redis.2.host = address2:6379
#redis.2.auth = password
``` 
Note: You can add multiple Redis server using syntax 'redis.[index]'.

###### Step 2-3: If your Redis configured with Sentinel ######
```bash
redis.1.prefix = my_redis1
redis.1.host = ip:26379,ip:26380
#redis.1.auth = password
redis.1.master = mastername
``` 

###### Step 2-4: Define your monitoring item in Redis ######
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
Please refer redis.io's url to define Redis's monitoring items that you want to monitor.
https://redis.io/commands/info

##### Step 3: Run #####
###### Windows ######
``` 
redis-monitor/pang.bat
``` 
###### Linux ######
Process will be launched in background.
``` 
redis-monitor/pang.sh
``` 
Check application's log
``` 
redis-monitor/log.sh
``` 
##### Step 4: Access your devices #####
See your device in Pangdata.com

Login your account.
See main dashborad and you can find registered device.
Create your own dashboard and you can see realtime of your data.

##### Step 5: You are happy to play with your data #####
Wow!! all done. Enjoy and play with your device and your data.

##### Feel our demo #####
Go to https://pangdata.com
You can login using demo username 'pang-demo' with password 'panggood'.
