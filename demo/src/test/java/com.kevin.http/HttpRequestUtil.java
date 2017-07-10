package com.kevin.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @描述：使用Http连接池进行get请求，大大提高并发能力
 * @作者：kevin
 * @时间：2017/7/10 15:53
 * @版本：1.0
 */
public class HttpRequestUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static PoolingHttpClientConnectionManager cm = null;

    static {
        // 自定义ConnectionSocketFactory
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            LOGGER.error("创建SSL连接失败");
        }

        // 将自定义的ConnectionSocketFactory与连接管理器cm整合
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory()).build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置最大连接数为200
        cm.setMaxTotal(200);
        // 设置每个路由的最大连接数为20
        cm.setDefaultMaxPerRoute(20);
    }

    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }

    public static String get(String url, Map<String, String> params) {
        // 创建默认的HttpClient实例
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            // 添加请求参数
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String str = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
            // 创建HttpGet请求
            HttpGet httpGet = new HttpGet(url + "?" + str);
            // 发送GET请求
            LOGGER.info("执行get请求, url: " + httpGet.getURI());
            httpResponse = httpClient.execute(httpGet);
            // 获取response实体
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String response = EntityUtils.toString(entity);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                LOGGER.info("响应状态码: " + statusCode);
                LOGGER.info("响应内容: " + response);
                if (statusCode == HttpStatus.SC_OK) {
                    return response;
                } else {
                    return null;
                }
            }
            return null;
        } catch (IOException e) {
            LOGGER.error("httpClient请求失败", e);
            return null;
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                } catch (IOException e) {
                    LOGGER.error("关闭response失败", e);
                }
            }
        }
    }
}
