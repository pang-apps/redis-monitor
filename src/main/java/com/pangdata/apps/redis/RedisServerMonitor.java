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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pangdata.sdk.mqtt.PangMqtt;
import com.pangdata.sdk.util.PangProperties;

public class RedisServerMonitor {
  private static final Logger logger = LoggerFactory.getLogger(RedisServerMonitor.class);

  private static Map<String, List<String>> mItems = new HashMap<String, List<String>>();

  private static AtomicBoolean running = new AtomicBoolean();

  public static void main(String[] args) throws Exception {
    Map<Integer, Map<String, String>> redis = PangProperties.extractVariableProperties("redis");
    logger.info("Target Redis servers: {}", redis.toString());
    if (redis.size() == 0) {
      logger.error("No redis target server found to monitor. Check your pang.properties");
      return;
    }

    loadTargetMonitoringItems();
    logger.info("Monitoring items: ", mItems.toString());
    if (mItems.size() == 0) {
      logger.error("No item found to monitor. Check your pang.properties.");
      return;
    }

    PangMqtt pang = null;
    try {
      pang = new PangMqtt();
    } catch (Exception e) {
      logger.error("Error occurred", e);
      return;
    }


    final ExecutorService executor = Executors.newFixedThreadPool(redis.size());
    running.set(true);

    Iterator<Entry<Integer, Map<String, String>>> iterator = redis.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<Integer, Map<String, String>> next = iterator.next();
      final Map<String, String> target = next.getValue();

      executor.execute(new ServerStateGetter(target, mItems, running, pang));
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        running.set(false);
        executor.shutdown();
      }
    });
    
    
  }

  private static void loadTargetMonitoringItems() {
    List<String> mainTarget = PangProperties.extractPrefixedProperties("monitor");
    logger.info("Monitoring sections: {}", mainTarget);
    for (int i = 0; i < mainTarget.size(); i++) {
      List<String> subTarget = PangProperties.extractPrefixedProperties(mainTarget.get(i));
      mItems.put(mainTarget.get(i), subTarget);
    }
  }
}
