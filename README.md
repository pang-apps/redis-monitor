# Monitoring your Redis Server on mobile where ever you are.
Redis Server monitoring application for performance and prediction of failure.
This application used Pangdata.com cloud service. Pangdata.com is cloud based monitoring analysis SaaS platform. 
You can monitor your cloud infrstructure and applications using Pang SDK.

## Screen shot
###### Realtime monitoring ######
![Realtime monitoring](https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard.PNG "Realtime monitoring")

###### Realtime monitoring on Mobile ######
![Mobile](https://github.com/pang-apps/redis-monitor/blob/master/screen-shot/dashboard-mobile.jpg "Realtime monitoring")

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

Confgiure your account and user key in pang.properties.
```bash
pang.username=your username in pangdata.com
pang.userkey=your user key in pangdata.com
``` 

Confgiure database connection properties
```bash


``` 

Declare your status variable.
```bash

``` 

Note: User key can be found in your profile of Pangdata.com
##### Step 3: Run #####
###### Windows ######
``` 
redis-monitor/pang.bat
``` 
###### Linux ######
``` 
redis-monitor/pang.sh
``` 
###### Background in Linux ######
```
nohup pang.sh > /dev/null 2>&1&
```
##### Step 4: Access your devices #####
Register your device in Pangdata.com

Login your account.
See main dashborad and you can find unregistered device.
Register the unregistered device.

##### Step 5: You are happy to play with your data #####
Wow!! all done. Enjoy and play with your device and your data.
