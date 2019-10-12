package com.ausky.utility.http;

import org.testng.annotations.Test;

import java.util.HashMap;

public class TestGet {

    @Test
    public void testGet() {
        System.out.println(Get.httpGet("http://www.baidu.com", new HashMap<>()));
    }
}
