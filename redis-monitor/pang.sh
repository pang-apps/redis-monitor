#!/usr/bin/env bash

nohup java -cp ./libs/*:./conf com.pangdata.apps.redis.RedisServerMonitor > /dev/null 2>&1&
