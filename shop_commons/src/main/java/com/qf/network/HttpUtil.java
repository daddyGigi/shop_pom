package com.qf.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by  .Life on 2019/07/12/0012 20:49
 */
public class HttpUtil {
    public static String sendGet(String urlstr){
        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ){
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            //发送请求到指定的服务器
            connection.connect();
            //获取服务器返回的结果
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024*10];
            int len;
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
           byte[] buffer = outputStream.toByteArray();
            return new String(buffer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
