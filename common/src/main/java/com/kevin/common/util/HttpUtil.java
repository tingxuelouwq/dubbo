package com.kevin.common.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @类名：HttpUtil
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/10 19:14
 * @版本：1.0
 * @描述：Http工具类
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 连接池最大连接数
     **/
    private static final int HTTPCLIENT_MAX_TOTAL = 200;
    /**
     * 单个路由最大连接数
     **/
    private static final int HTTPCLIENT_DEFAULT_MAX_PER_ROUTE = 20;
    /**
     * 从连接管理器获取连接的超时时间
     **/
    private static final int HTTPCLIENT_CONNECTION_REQUEST_TIMEOUT = 1000;
    /**
     * 建立连接的超时时间
     **/
    private static final int HTTPCLIENT_CONNECT_TIMEOUT = 5000;
    /**
     * 请求获取数据的超时时间
     **/
    private static final int HTTPCLIENT_SOCKET_TIMEOUT = 300000;
    /**
     * 请求重试次数
     **/
    private static final int HTTPCLIENT_RETRY_COUNT = 3;

    // 连接管理器
    private static PoolingHttpClientConnectionManager cm = null;

    // 请求重试机制
    private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount > HTTPCLIENT_RETRY_COUNT) {
                // Do not retry if over 3 retry count
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                // Timeout
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // Unknown host
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // Connection refused
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    };

    static {
        // 创建http连接管理器，使用连接池管理http连接
        cm = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        cm.setMaxTotal(HTTPCLIENT_MAX_TOTAL);
        // 设置每个路由的最大连接数
        cm.setDefaultMaxPerRoute(HTTPCLIENT_DEFAULT_MAX_PER_ROUTE);
    }

    private HttpUtil() {
        // no constructor function
    }

    /**
     * @return
     * @方法名：getHttpClient
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/10 19:49
     * @描述：获取HttpClient
     */
    private static CloseableHttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(HTTPCLIENT_CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(HTTPCLIENT_CONNECT_TIMEOUT)
                .setSocketTimeout(HTTPCLIENT_SOCKET_TIMEOUT)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(requestRetryHandler)   // 默认重试3次
                .build();
        return httpClient;
    }

    /**
     * GET请求，针对文本
     * @param url
     * @return
     */
    public static String get(String url) {
        log.info("-------HttpClient GET开始---------");
        log.info("GET: " + url);
        if (StringUtil.isEmpty(url)) {
            log.error("GET请求不合法，请检查uri参数!");
            return null;
        }

        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        String respContent = null;
        try {
            // 创建GET请求
            httpGet = new HttpGet(url);
            // 执行GET请求
            response = httpClient.execute(httpGet);
            // 获取响应内容
            respContent = getRespContent(response, "GET");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("-------HttpClient GET结束----------");
        return respContent;
    }

    /**
     * 获取响应，针对文本
     * @param response
     * @param method
     * @return
     * @throws IOException
     */
    private static String getRespContent(HttpResponse response, String method) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        String reasonPhrase = statusLine.getReasonPhrase();
        String respContent = null;

        if (statusCode == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            log.info(entity.getContentType().toString());
            if (entity != null) {
                respContent = EntityUtils.toString(entity);
            }
        } else {
            log.error(method + ": statusCode[" + statusCode + "], desc[" + reasonPhrase + "]");
        }

        return respContent;
    }

    /**
     * GET请求，针对多媒体，请求成功后，会直接从对方服务器下载多媒体文件到本地磁盘
     * @param url
     * @param absoluteFilePath
     * @return
     */
    public static String get(String url, String absoluteFilePath) {
        log.info("-------HttpClient GET开始---------");
        log.info("GET: " + url);
        if (StringUtil.isEmpty(url)) {
            log.error("GET请求不合法，请检查uri参数!");
            return null;
        }

        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            // 创建GET请求
            httpGet = new HttpGet(url);
            // 执行GET请求
            response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 写文件到本地磁盘
                    InputStream in = entity.getContent();
                    result = FileUtil.writeFile(in, absoluteFilePath);
                } else {
                    log.error("GET: statusCode[" + statusCode + "], desc[" + reasonPhrase + "]");
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("-------HttpClient GET结束----------");
        return result;
    }

    /**
     * POST请求，请求参数为json格式
     * @param url
     * @param jsonStr
     * @return
     */
    public static String post(String url, String jsonStr) {
        log.info("-------HttpClient POST开始----------");
        log.info("POST: " + url);
        if (StringUtil.isEmpty(url)) {
            log.error("POST请求不合法，请检查uri参数!");
            return null;
        }
        log.info("req: " + jsonStr);


        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String respContent = null;
        try {
            // 创建POST请求
            httpPost = new HttpPost(url);
            // 执行POST请求
            StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
            entity.setContentType("application/json; charset=UTF-8");
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            // 获取响应内容
            respContent = getRespContent(response, "POST");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("-------HttpClient POST结束----------");
        return respContent;
    }

    /**
     * POST请求，请求参数为表单
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, List<NameValuePair> params) {
        log.info("-------HttpClient POST开始----------");
        log.info("POST: " + url);
        if (StringUtil.isEmpty(url)) {
            log.error("POST请求不合法，请检查uri参数!");
            return null;
        }
        log.info("req: " + params);


        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String respContent = null;
        try {
            // 创建POST请求
            httpPost = new HttpPost(url);
            // 执行POST请求
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            response = httpClient.execute(httpPost);
            // 获取响应内容
            respContent = getRespContent(response, "POST");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("-------HttpClient POST结束----------");
        return respContent;
    }

    /**
     * POST请求，请求参数为Json格式
     * @param url
     * @param jsonStr
     * @return 响应状态码
     */
    public static int postAndGetStatusCode(String url, String jsonStr) {
        log.info("-------HttpClient POST开始----------");
        log.info("POST: " + url);
        if (StringUtil.isEmpty(url)) {
            log.error("POST请求不合法，请检查uri参数!");
            return 400;
        }
        log.info("req: " + jsonStr);


        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        int respStatusCode = 200;
        try {
            // 创建POST请求
            httpPost = new HttpPost(url);
            // 执行POST请求
            StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
            entity.setContentType("application/json; charset=UTF-8");
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            // 获取响应内容
            respStatusCode = getRespStatusCode(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("-------HttpClient POST结束----------");
        return respStatusCode;
    }

    /**
     * 获取响应状态码
     * @param response
     * @return
     * @throws IOException
     */
    private static int getRespStatusCode(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        return statusCode;
    }
}
