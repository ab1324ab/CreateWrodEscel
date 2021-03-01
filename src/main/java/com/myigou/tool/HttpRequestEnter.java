package com.myigou.tool;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import java.util.Map;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/5/15.
 */
public class HttpRequestEnter {

    public static String doPostStr(String url,Map<String,String> map) {
        HttpClient httpClient = new HttpClient();
        try {
            PostMethod postMethod = new PostMethod("http://www.nacei.cn/version");
            postMethod.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
            httpClient.getParams().setContentCharset("UTF-8");
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            NameValuePair[] requestBody = new NameValuePair[map.size()];
            int i = 0;
            for(String key : map.keySet()){
                String value = map.get(key);
                requestBody[i] = new NameValuePair(key, value);
                i++;
            }
            postMethod.setRequestBody(requestBody);
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                String retData = postMethod.getResponseBodyAsString();
                System.out.println("Request POST SUCCESS !");
                return retData;
            } else {
                System.out.println("HttpStatus=" + statusCode + " ; Request POST ERROR !");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return "";
    }

    public static String doGetStr(String url) {
        String result = "";
        try {
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(url);
            getMethod.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
            httpClient.getParams().setContentCharset("UTF-8");
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            //httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
            int statusCode = httpClient.executeMethod(getMethod);
            if(statusCode == HttpStatus.SC_OK){
                result = getMethod.getResponseBodyAsString();
                System.out.println("Request GET SUCCESS !");
            }else{
                System.out.println("HttpStatus=" + statusCode + " ; Request GET ERROR !");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
}

