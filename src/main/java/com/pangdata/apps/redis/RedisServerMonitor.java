/*
 * Copyright (c) 2015 Preversoft
 */

package com.pangdata.apps.redis;

import java.util.ArrayList;
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

  private static AtomicBoolean running = new AtomicBoolean();

  private static List<String> mItems;


  public static void main(String[] args) throws Exception {
    Map<Integer, Map<String, String>> redis = PangProperties.extractVariableProperties("redis");
    logger.info("Target Redis servers: {}", redis.toString());
    if (redis.size() == 0) {
      logger.error("No redis target server found to monitor. Check your pang.properties");
      return;
    }

    mItems = loadTargetMonitoringItems();

    if (mItems == null || mItems.size() == 0) {
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

  private static List<String> loadTargetMonitoringItems() {
    Map<String, Object> mainTarget = PangProperties.extractPrefixedProperties("monitor");
    logger.info("Monitoring sections: {}", mainTarget);
    
    List<String> enabledSections = new ArrayList<String>();
    
    Iterator<Entry<String, Object>> iterator = mainTarget.entrySet().iterator();
    while(iterator.hasNext()) {
      Entry<String, Object> next = iterator.next();
      String section = next.getKey();
      if(String.valueOf(next.getValue()).equalsIgnoreCase("true")) {
        enabledSections.add(section.substring(section.indexOf(".") + 1));
      }
    }
    
    logger.info("Enabled items: {}", enabledSections);
    return enabledSections;
    
//    for (int i = 0; i < enabledSections.size(); i++) {
//      Map<String, Object> subTarget = PangProperties.extractPrefixedProperties(enabledSections.get(i));
//      List<String> items = new ArrayList<String> ();
//      Iterator<Entry<String, Object>> iterator2 = subTarget.entrySet().iterator();
//      while(iterator2.hasNext()) {
//        Entry<String, Object> next = iterator2.next();
//        String item = next.getKey();
//        if(String.valueOf(next.getValue()).equalsIgnoreCase("true")) {
//          items.add(item.substring(item.indexOf(".") + 1));
//        }
//      }
//      mItems.put(enabledSections.get(i), items);
//      logger.info("{} items: {}", enabledSections.get(i), items.toString());;
//    }
  }
}
