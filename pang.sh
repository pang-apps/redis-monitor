#!/usr/bin/env bash

nohup java -Xmx64m -cp ./libs/*:./conf com.pangdata.apps.redis.RedisServerMonitor > /dev/null 2>&1&