package com.ausky.utility.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Get {
    private static final Logger LOGGER = LoggerFactory.getLogger(Get.class);

    /**
     * @param url    请求地址
     * @param params 请求参数
     */
    public static String httpGet(String url, Map<String, String> params, Map<String, String>... requestConfig) {
        LOGGER.info("start get url : {}", url);
        String result = null;
        //获取Http客户端（可以理解为:你得先有一个浏览器；注意：实际上HttpClient与浏览器是不一样的）
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //创建Get请求
        HttpGet httpGet = new HttpGet(dealWithParam(url, params));
        httpGet.setConfig(dealWithRequestConfig(requestConfig.length == 0 ? new HashMap<>() : requestConfig[0]));

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            LOGGER.info("响应状态为：{}", response.getStatusLine());

            if (httpEntity != null) {
                LOGGER.info("响应内容长度为:{} ", httpEntity.getContentLength());

                String responseData = EntityUtils.toString(httpEntity);
                LOGGER.info("响应内容为: {}", responseData);

                result = responseData;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     * 进行参数的处理
     *
     * @param url
     * @param params
     * @return
     */
    private static String dealWithParam(String url, Map<String, String> params) {

        String result = null;
        StringBuilder stringBuilder = new StringBuilder(url);
        try {
            stringBuilder.append(url.contains("?") ? "" : "?");
            for (String key : params.keySet()) {
                stringBuilder.append("name=" + URLEncoder.encode(params.get(key), "utf-8"));
                stringBuilder.append("%");
            }

            result = params.isEmpty() ? stringBuilder.toString() : stringBuilder.substring(0, stringBuilder.length() - 2);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param requestConfig
     * @return
     */
    private static RequestConfig dealWithRequestConfig(Map<String, String> requestConfig) {
        return RequestConfig.custom()
                .setConnectTimeout(requestConfig.get("connectTimeout") == null ? 5000 : Integer.parseInt(requestConfig.get("connectTimeout")))
                .setConnectionRequestTimeout(requestConfig.get("connectionRequestTimeout") == null ? 5000 : Integer.parseInt(requestConfig.get("connectionRequestTimeout")))
                .setSocketTimeout(requestConfig.get("socketTimeout") == null ? 5000 : Integer.parseInt(requestConfig.get("socketTimeout")))
                .setRedirectsEnabled(true)
                .build();
    }
}
