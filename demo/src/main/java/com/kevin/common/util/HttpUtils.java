package com.kevin.common.util;

import com.alibaba.fastjson.JSONObject;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名：HttpUtils
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/10 19:14
 * @版本：1.0
 * @描述：Http工具类
 */
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

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
    private static final int HTTPCLIENT_CONNECT_TIMEOUT = 15000;
    /**
     * 请求获取数据的超时时间
     **/
    private static final int HTTPCLIENT_SOCKET_TIMEOUT = 120000;
    /**
     * 请求重试次数
     **/
    private static final int HTTPCLIENT_RETRY_COUNT = 3;

    // mime类型映射表
    private static Map<String, String> mimeTypeMap = null;

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

    static {
        mimeTypeMap = new HashMap<>();
        mimeTypeMap.put("image/png", "png");
        mimeTypeMap.put("image/jpg", "jpg");
        mimeTypeMap.put("image/jpeg", "jpeg");
        mimeTypeMap.put("image/gif", "gif");
        mimeTypeMap.put("image/bmp", "bmp");
    }

    private HttpUtils() {
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
     * @param url 请求url，参数封装
     * @return 响应字符串
     * @方法名：getForNonMedia
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/11 9:07
     * @描述：GET请求，针对非多媒体文件（文本，json等）
     */
    public static String getText(String url) {
        log.info("-------HttpClient GET开始---------");
        log.info("GET: " + url);
        if (StringUtils.isEmpty(url)) {
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
     * @param url              请求url，参数封装
     * @param absoluteFilePath 文件存放目标绝对路径（包含文件）
     * @return java.lang.String 操作成功，则返回文件绝对路径名，否则返回null
     * @throws
     * @方法名：getImage
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/7 9:33
     * @描述：GET请求，针对图片，请求成功后，会直接从对方服务器下载多媒体文件到本地磁盘
     */
    public static String getImage(String url, String absoluteFilePath) {
        log.info("-------HttpClient GET开始---------");
        log.info("GET: " + url);
        if (StringUtils.isEmpty(url)) {
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
                    String mimeType = entity.getContentType().getValue();
                    if (mimeType.indexOf(";") != -1) {
                        mimeType = mimeType.substring(0, mimeType.indexOf(";"));
                    }
                    log.info("MimeType: " + mimeType);

                    if (mimeTypeMap.get(mimeType) != null) {
                        // 重命名文件名
                        absoluteFilePath = absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf(".") + 1) + mimeTypeMap.get(mimeType);
                        // 写文件到本地磁盘
                        InputStream in = entity.getContent();
                        result = FileUtils.writeFile(in, absoluteFilePath);
                    }
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
     * @param url              请求url，参数封装
     * @param absoluteFilePath 文件存放目标绝对路径（包含文件）
     * @return java.lang.String 操作成功，则返回文件绝对路径名，否则返回null
     * @throws
     * @方法名：getMedia
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/7 9:33
     * @描述：GET请求，针对除图片之外的多媒体，请求成功后，会直接从对方服务器下载多媒体文件到本地磁盘
     */
    public static String getMedia(String url, String absoluteFilePath) {
        log.info("-------HttpClient GET开始---------");
        log.info("GET: " + url);
        if (StringUtils.isEmpty(url)) {
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
                    result = FileUtils.writeFile(in, absoluteFilePath);
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
     * @方法名：post
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/31 15:38
     * @描述：post请求-json串
     * @param url
     * @param jsonStr
     * @return java.lang.String
     * @exception
     */
    public static String post(String url, String jsonStr) {
        log.info("-------HttpClient POST开始----------");
        log.info("POST: " + url);
        if (StringUtils.isEmpty(url)) {
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
     * @方法名：post
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/31 15:38
     * @描述：post请求-json串
     * @param url
     * @param json
     * @return java.lang.String
     * @exception
     */
    public static String post(String url, JSONObject json) {
        log.info("-------HttpClient POST开始----------");
        log.info("POST: " + url);
        if (StringUtils.isEmpty(url)) {
            log.error("POST请求不合法，请检查uri参数!");
            return null;
        }
        log.info("req: " + json.toJSONString());


        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String respContent = null;
        try {
            // 创建POST请求
            httpPost = new HttpPost(url);
            // 执行POST请求
            StringEntity entity = new StringEntity(json.toJSONString(), Charset.forName("UTF-8"));
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
     * @param url    请求地址
     * @param params key-value形式的参数列表
     * @return java.lang.String 响应字符串
     * @方法名：post请求-表单
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/19 16:39
     * @描述：POST请求
     */
    public static String post(String url, List<NameValuePair> params) {
        log.info("-------HttpClient POST开始----------");
        log.info("POST: " + url);
        if (StringUtils.isEmpty(url)) {
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
     * @方法名：postAndGetStatusCode
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/31 15:38
     * @描述：post请求-json串
     * @param url
     * @param jsonStr
     * @return java.lang.String
     * @exception
     */
    public static int postAndGetStatusCode(String url, String jsonStr) {
        log.info("-------HttpClient POST开始----------");
        log.info("POST: " + url);
        if (StringUtils.isEmpty(url)) {
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
     * @param response HttpResponse对象
     * @return 响应字符串
     * @方法名：getRespStatusCode
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/11 9:21
     * @描述：获取响应状态码，针对MimeType为text/plain、text/json格式
     */
    private static int getRespStatusCode(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        return statusCode;
    }

    /**
     * @param response HttpResponse对象
     * @param method   请求方式
     * @return 响应字符串
     * @方法名：getRespContent
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/11 9:21
     * @描述：获取响应内容，针对MimeType为text/plain、text/json格式
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
}
