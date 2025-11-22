package com.myigou.tool;


import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ab1324ab
 * Created by ab1324ab on 2017/5/15.
 */
public class HttpRequestEnter {

    public static String doPostStr(String url, Map<String, String> map) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            List<BasicNameValuePair> params = new ArrayList<>();
            for (String key : map.keySet()) {
                String value = map.get(key);
                params.add(new BasicNameValuePair(key, value));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    String retData = EntityUtils.toString(response.getEntity());
                    System.out.println("Request POST SUCCESS !");
                    return retData;
                } else {
                    System.out.println("HttpStatus=" + statusCode + " ; Request POST ERROR !");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.err.println("Error closing HttpClient: " + e.getMessage());
            }
        }
        return "";
    }

    public static String doGetStr(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "application/json"); // 可以根据需要设置其他头信息

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    String retData = EntityUtils.toString(response.getEntity());
                    System.out.println("Request GET SUCCESS !");
                    return retData;
                } else {
                    System.out.println("HttpStatus=" + statusCode + " ; Request GET ERROR !");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.err.println("Error closing HttpClient: " + e.getMessage());
            }
        }
        return "";
    }
}

