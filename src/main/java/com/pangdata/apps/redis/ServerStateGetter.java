/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Preversoft
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.pangdata.apps.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Pool;

import com.pangdata.sdk.mqtt.PangMqtt;
import com.pangdata.sdk.util.NumberUtils;
import com.pangdata.sdk.util.PangProperties;

public class ServerStateGetter implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(ServerStateGetter.class);

  private Map<String, String> redis;
  private List<String> mItems;
  private AtomicBoolean running;

  private PangMqtt pang;

  private String prefix;

  public ServerStateGetter(Map<String, String> redis, List<String> mItems, AtomicBoolean running,
      PangMqtt pang) {
    this.redis = redis;
    this.mItems = mItems;
    this.running = running;
    this.pang = pang;
  }

  public void run() {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setBlockWhenExhausted(false);
    config.setTestOnBorrow(true);
    
    Jedis resource = null;
    Pool<Jedis> jedisPool = null;
    String host  = null;
    
    try {
      prefix = redis.get("prefix");
      if (prefix == null || prefix.length() == 0) {
        throw new IllegalArgumentException("'redis.[index].prefix' must not be null");
      }
  
      Thread.currentThread().setName(prefix);
  
      host = redis.get("host");
      String[] addr = host.split(":");
  
      if (host == null || host.trim().length() == 0 || addr == null || addr.length == 0) {
        throw new IllegalArgumentException("redis.[index].host' property not configured properly");
      }
    
      String master = redis.get("master");
      if(master != null) {
        Set<String> sentinels = new HashSet<String>();
  
        String[] split = host.split(",");
        for (String uri : split) {
          sentinels.add(uri); // default sentinel port is that!
        }
        jedisPool = new JedisSentinelPool(master, sentinels, config, 5000, redis.get("auth"));
      } else {
        jedisPool =
            new JedisPool(config, addr[0], addr.length == 1 ? 6379 : Integer.valueOf(addr[1]), 5000,
                redis.get("auth")); 
      }
    } catch (Exception e) {
      logger.error("Connection error", e);
      System.exit(0);
    }
    
    while (running.get()) {
      try {
        resource = jedisPool.getResource();

        String result = resource.info();
        Map<String, Object> values = parse(mItems, result);
        logger.debug("Redis info: {}", values);
        pang.sendData(values);
      } catch (Throwable e) {
        logger.error("Error occurred", e);
        if (e instanceof JedisConnectionException) {
          logger.error("Redis Server connection failure: {}", host);
        }
      } finally {
        jedisPool.returnResourceObject(resource);
      }

      try {
        TimeUnit.MILLISECONDS.sleep(PangProperties.getPeriod());
      } catch (InterruptedException e) {
      }
    }

    if (resource != null) {
      jedisPool.returnResourceObject(resource);
    }
  }

  private Map<String, Object> parse(List<String> mItems, String result) {
    String[] split = result.split("\r\n");
    Map<String, Object> values = new HashMap<String, Object>();
    for (int i = 0; i < split.length; i++) {
      String[] split2 = split[i].split(":");

      if (mItems.contains(split2[0])) {
        values.put(prefix + "_" + split2[0], NumberUtils.toObject(split2[1]));
      } else if (split2[0].startsWith("db") && split2.length >= 2) {
        // prefix_dbindex_keys, prefix_dbindex_expires, prefix_dbindex_avg_ttl
        String[] split3 = split2[1].split(",");
        for (int j = 0; j < split3.length; j++) {
          String[] split4 = split3[j].split("=");
          if (mItems.contains(split4[0])) {
            values.put(prefix + "_" + split2[0] + "_" + split4[0], NumberUtils.toObject(split4[1]));
          }
        }
      }
    }
    return values;
  }
}
