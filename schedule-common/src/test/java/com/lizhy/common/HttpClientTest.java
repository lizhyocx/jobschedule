package com.lizhy.common;

import com.lizhy.common.http.HttpClientManager;
import com.lizhy.common.http.HttpResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class HttpClientTest {
    public static void main(String[] args) {
        String url = "http://xxxxxx";
        Map<String,String> param = new HashMap<String,String>();
        //param.put("name", "test");
        HttpResult result = HttpClientManager.executePost(url, param);
        System.out.println(result);
    }
}
