package com.lizhy.common.http;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class HttpClientManager {
    private static Logger logger = LoggerFactory.getLogger(HttpClientManager.class);
    private static PoolingHttpClientConnectionManager poolingConnManager;
    private static final int SOCKET_TIMEOUT = 10000;
    private static final int CONN_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;
    static {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,
                    new TrustSelfSignedStrategy())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();
            poolingConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolingConnManager.setMaxTotal(200);
            poolingConnManager.setDefaultMaxPerRoute(20);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
            poolingConnManager.setDefaultSocketConfig(socketConfig);
        } catch (NoSuchAlgorithmException e) {
            logger.error("HttpClientManager init exception", e);
        } catch (KeyManagementException e) {
            logger.error("HttpClientManager init exception", e);
        } catch (KeyStoreException e) {
            logger.error("HttpClientManager init exception", e);
        }
    }

    public static CloseableHttpClient getConnection() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolingConnManager)
                .build();
        return httpClient;
    }

    public static HttpResult executePost(String url, Map<String, String> params) {
        if(StringUtils.isNotBlank(url)) {
            CloseableHttpClient httpClient = getConnection();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(READ_TIMEOUT)
                    .setConnectTimeout(CONN_TIMEOUT)
                    .build();//设置请求和传输超时时间
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            List<BasicNameValuePair> paraList = new ArrayList<BasicNameValuePair>(params.size());
            for (Map.Entry<String, String> pEntry : params.entrySet()) {
                if(null != pEntry.getValue()){
                    BasicNameValuePair nv = new BasicNameValuePair(pEntry.getKey(), pEntry.getValue());
                    paraList.add(nv);
                }
            }
            //使用UTF-8
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paraList, Charset.forName("UTF-8"));
            post.setEntity(formEntity);
            CloseableHttpResponse response=null;
            try {
                response = httpClient.execute(post);
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode >= 200 && statusCode < 303) {
                    HttpEntity entity = response.getEntity();
                    return new HttpResult(true, statusCode, entity.toString());
                }
                return new HttpResult(false, statusCode, null);
            } catch (Exception e) {
                post.abort();
                logger.error("executePost exception:url="+url,e);
                return new HttpResult(false, -1, "exeute post exception:"+e.getMessage());
            }
        }
        return new HttpResult(false, -1, "post url is null");
    }

}
