# 随时随地，可便捷的监测您的Redis服务器.通过各种分析功能，诊断服务器的性能周期
通过预测功能，预防服务器故障，可稳定运营状态.
Redis 监测是使用(Pangdata.com)应用程序开发的。所有的存储，分析，监测技术都是Pangdata云服务所提供，把应用安装在大家的服务器上就可以了
 
# 基于云监测，分析服务
庞数据是 SaaS(Software as a Service)类型，所以快速便捷，强大的分析技术，让大家的基础设施(infrastructure)变得更有价值
又通过 <a href="https://github.com/pangdata/pang-sdk-java">Pang SDK</a>的使用，可监测大家想要监测的基础设施(Infrastructure)

###### 庞数据云监测服务 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/Cloud Monitoring Service.JPG" width="1000" />

## 屏幕截图
###### 实时监测仪表盘 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard.PNG" width="600" />

###### 移动中实时仪表板(所用功能与电脑相同) ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard-mobile.jpg" width="300" />

###### 以注册的Redis监测项目表 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/devices.PNG" width="300" />

###### CPU 统计分析 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-cpu.PNG" width="300" />

###### Memory 统计分析 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-memory.PNG" width="300" />

###### Stats 统计分析 ######
<img src="https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/analytics-stats.PNG" width="300" />

## 开始
#### Pangdata.com 注册 ####
安装Redis 监测应用程序之前要注册。通过邮箱即可加入.
进 <a href="http://pangdata.com" target="_blank">https://pangdata.com</a> 注册后进入设置找到用户密钥，配置庞App
#### 为运行庞App需要以下软件 ####
 **Java 1.5+** 版本或以上版本。
#### 快速便捷的安装 ####
安装只需5分钟^^.

##### 第1阶段 #####
###### Windows ######
列表中是最新发布的 http://pan.baidu.com/s/1bpcRMUV 下载redis-monitor.zip文件, 在要安装的路径上解压
###### Linux ######
列表中是最新发布的 http://pan.baidu.com/s/1bpcRMUV 下载redis-monitor.tar文件, 在要安装的路径上解压
``` 
tar -xvf redis-monitor.tar
``` 
##### 第2阶段: pang.properties 文件设置 #####

redis-monitor/conf/pang.properties文件在Notepad++ vi(Linux)打开修改即可
 
######2-1: 用户ID和用户密钥设置 ######
```bash
pang.username=用户ID
pang.userkey=用户密钥 
``` 
Note: 注册后，进入设置既可看到用户密钥.
 
###### 2-2: 监测对象 Redis 服务器设置 ######

Note: prefix 不能使用空格.
```bash
redis.1.prefix = my_redis1
redis.1.host = address:6379
#redis.1.auth = password
#redis.2.prefix = my_redis2
#redis.2.host = address2:6379
#redis.2.auth = password
``` 
Note: 多个服务器配置时，使用 index 增加既可('redis.[index]').
 
###### 2-3: 用Redis 配置Sentinel的情况. ######

```bash
redis.1.prefix = my_redis1
redis.1.host = ip:26379,ip:26380
#redis.1.auth = password
redis.1.master = mastername
```
######  2-4: 设置 Redis监测项目 ######
添加新Redis监测项目时，前缀必须写monitor.
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
想找其他监测项目，那就访问 redis.io.确认即可. 以下是 按Section 监测的详细项目.
https://redis.io/commands/info
 
#####  第3阶段: 运行 #####

###### Windows ######
``` 
redis-monitor/pang.bat
``` 
###### Linux ######
会在后台模式运行。
``` 
redis-monitor/pang.sh
``` 
检查应用程序log. 有配置问题驱动时会发生错误.一定要确认logs/pang.log
``` 
redis-monitor/log.sh
```

##### 第4阶段: 确认您的设备 #####
登录Pangdata.com后查看您的设备列表，确认监测的项目.
创建仪表盘，使用各种小部件，来实时监测您的服务器.
 
##### 第5阶段: 现在开始，大家可以享受，数据带来的乐趣。 #####
所有的配置已结束。通过数据发现新的价值吧.
一天，一周，一个月时间，通过统计分析，看一看大家的服务怎么茁壮成长
 
##### 可以使用Demo感受一下吧 #####
https://pangdata.com 用户名ID ： pang-demo-cn  密码： panghao 来登录.

既可看到，Redis Server 实时监测的图表.
