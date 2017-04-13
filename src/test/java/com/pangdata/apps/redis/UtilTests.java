package com.pangdata.apps.redis;

import org.junit.Assert;
import org.junit.Test;

public class UtilTests {

  @Test
  public void spaceTest() {
    String s = "a b c";
    s = s.replaceAll("\\s", "");
    Assert.assertTrue(s.equals("abc"));
    System.out.println(s);
  }
  
}
