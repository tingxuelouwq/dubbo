package com.kevin.http;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述：HttpClient使用详解
 * @作者：kevin
 * @时间：2017/7/8 9:26
 * @版本：1.0
 * @使用方式：
 * 1、创建HttpClient对象
 * 2、创建请求方法实例，并指定请求url。如果需要发送GET请求，则创建HttpGet对象；如果需要发送POST请求，则创建HttpPost对象
 * 3、如果需要发送请求参数，可调用HttpGet、HttpPost类中的setParams(HttpParams params)方法来设置请求参数；对应HttpPost
 * 对象，还可以调用setEntity(HttpEntity entity)方法来设置请求参数
 * 4、调用HttpClient的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse对象
 * 5、调用HttpResponse的getAllHeader()、getHeaders(String name)等方法获取响应头，调用getEntity()方法获取HttpEntity
 * 对象，该对象包装了响应内容，包括响应长度、响应类型、响应编码、响应实体
 */
public class HttpClientTest {

    /**
     * Http Get请求
     */
    @Test
    public void httpGetTest() {
        CloseableHttpClient httpClient = null;

        try {
            // 创建默认的HttpClient实例
            httpClient = HttpClients.createDefault();

            // 创建HttpGet
            String uri = "https://www.baidu.com";
            HttpGet httpGet = new HttpGet(uri);
            System.out.println("executing request: " + httpGet.getURI());

            // 执行GET请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应类型
                    System.out.println("Response content length: " + entity.getContentType());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
                System.out.println("--------------");
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接，释放资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Http Post请求
     */
    @Test
    public void httpPostTest() {
        CloseableHttpClient httpClient = null;
        try {
            // 创建默认的HttpClient实例
            httpClient = HttpClients.createDefault();
            // 创建HttpPost
            String uri = "http://localhost:8090/api/articles/publishment";
            HttpPost httpPost = new HttpPost(uri);
            // 设置请求参数
            List<NameValuePair> urlParameter = new ArrayList<>();
            urlParameter.add(new BasicNameValuePair("ids", "1001,1002,1003"));
            urlParameter.add(new BasicNameValuePair("cIds", "100, 101"));
            urlParameter.add(new BasicNameValuePair("channelType", "web"));
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameter, "UTF-8"));
            System.out.println("executing request: " + httpPost.getURI());
            // 执行POST请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("----------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("----------");
                }
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件上传
     */
    @Test
    public void uploadTest() {
        CloseableHttpClient httpClient = null;
        try {
            // 创建默认的HttpClient实例
            httpClient = HttpClients.createDefault();
            // 创建HttpPost
            String uri = "http://localhost:8090/api/articles/upload";
            HttpPost httpPost = new HttpPost(uri);
            // 设置请求参数
            String path = "C:\\Users\\kevin\\Pictures\\国家大剧院.jpg";
            FileBody file = new FileBody(new File(path));
            StringBody desc = new StringBody("这是一张国家大剧院的图片", ContentType.create("text/plain", Consts.UTF_8));
            HttpEntity reqEntity = MultipartEntityBuilder.create().
                    // 以下两行代码解决图片中文乱码问题
                            setMode(HttpMultipartMode.BROWSER_COMPATIBLE).
                            setCharset(Consts.UTF_8).
                            addPart("file", file).
                            addPart("desc", desc).build();
            httpPost.setEntity(reqEntity);
            System.out.println("executing request: " + httpPost.getRequestLine());
            // 执行POST请求
            HttpResponse response = httpClient.execute(httpPost);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println("----------");
                System.out.println("Response content length: " + entity.getContentLength());
                System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                System.out.println("----------");
            }
            // 销毁
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件下载
     */
    @Test
    public void downloadTest() {
        CloseableHttpClient httpClient = null;
        try {
            // 创建默认的HttpClient实例
            httpClient = HttpClients.createDefault();
            // 创建HttpGet
            String url = "https://www.baidu.com/img/bd_logo1.png";
            HttpGet httpGet = new HttpGet(url);
            // 执行GET请求
            HttpResponse response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String downloadPath = "D:\\";
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                File downloadFile = new File(downloadPath);
                if (!downloadFile.exists()) {
                    downloadFile.mkdirs();
                }

                InputStream is = entity.getContent();
                OutputStream os = new FileOutputStream(new File(downloadFile, fileName));
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                is.close();
                os.flush();
                os.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置超时
     */
    @Test
    public void connectionTimeoutTest() {
        CloseableHttpClient httpClient = null;
        try {
            // 创建默认的HttpClient实例
            httpClient = HttpClients.createDefault();
            // 创建HttpGet
//            String uri = "http://www.youtube.com/";
            String uri = "http://localhost:8090/api/articles/upload";
            HttpGet httpGet = new HttpGet(uri);
            // 设置连接超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(1000)  // 设置从ConnectionManager获取连接的超时时间，单位毫秒
                    .setConnectTimeout(1000)    // 设置建立连接超时时间，单位毫秒。如果访问外网而不翻墙的话，就会抛出ConnectTimeoutException异常
                    .setSocketTimeout(2000).build();    // 设置请求获取数据超时时间，单位毫秒。如果建立连接成功，但获取数据超时，则抛出SocketTimeoutException异常
            httpGet.setConfig(requestConfig);
            // 执行GET请求
            HttpResponse response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "UTF-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 请求重试
     */
    @Test
    public void requestRetryTest() {
        CloseableHttpClient httpClient = null;
        try {
            // 创建默认的HttpClient
            HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    if (executionCount > 3) {
                        // Do not retry if over max retry count
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
                    if (exception instanceof ConnectionPoolTimeoutException) {
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
            httpClient = HttpClients.custom().setRetryHandler(retryHandler).build();
            // 创建HttpGet
            String uri = "http://localhost:8090/api/articles/upload";
            HttpGet httpGet = new HttpGet(uri);
            // 设置连接超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(1000)
                    .setConnectTimeout(5000)
                    .setSocketTimeout(2000)
                    .build();
            httpGet.setConfig(requestConfig);
            // 执行GET请求
            HttpResponse response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "UTF-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        CloseableHttpClient httpClient = null;
        try {
            // 创建默认的HttpClient
            httpClient = HttpClients.createDefault();
            // 创建HttpGet
//            String uri = "http://localhost:8090";
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8090)
                    .setPath("/api/articles/publishment")
                    .setParameter("ids", "1001,1002,1003")
                    .setParameter("cids", "100,101")
                    .setParameter("channelType", "web")
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            System.out.println(httpGet.getURI());
            // 执行Get请求
            CloseableHttpResponse response = null;
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
