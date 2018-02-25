package com.myigou.tool;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ab1324ab
 *         Created by ab1324ab on 2017/5/15.
 */
public class HttpRequestEnter {

    public static String versionEscel() {
        HttpClient client = new DefaultHttpClient();
        try {
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60);
            // testh.html
            HttpGet httpGet = new HttpGet("http://123.207.93.222/version.html");
            HttpResponse response = client.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();
                inputStream.close();
                reader.close();
                System.out.println("Request SUCCESS !");
                return line;
            } else {
                System.out.println("HttpStatus=" + status + " ; Request ERROR !");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return "";
    }
}

