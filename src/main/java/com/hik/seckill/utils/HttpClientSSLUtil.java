package com.hik.seckill.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpClientSSLUtil {

    private static HttpClient client = null;

    // 连接超时
    protected static final Integer DEFAULT_CONNECTION_TIME_OUT = 15000;

    // 读取超时
    protected static final Integer DEFAULT_SOCKET_TIME_OUT = 15000;

    protected static final String DEFAULT_CHAR_SET = "UTF-8";

    protected static final String DEFAULT_HTTPS = "https";


    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    private static HttpResponse doPostDefaultSecurity(String url, String jsonText) throws Exception {
        return doPost(url, jsonText, null);
    }

    private static HttpResponse doPostDefaultSecurity(String url, String jsonText, Map<String, String> header) throws Exception {
        return doPost(url, jsonText, header);
    }

    public static StreamClosedHttpResponse doPostStringSecurity(String url, String jsonText, Map<String, String> header) throws Exception {
        return doPostForString(url, jsonText, header);
    }

    public static String doPostToStringSecurity(String url, String jsonText, Map<String, String> header) throws Exception {
        return doPostToString(url, jsonText, header);
    }

    public static String doParamPostToString(String url, Map<String, String> param) throws Exception {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded");
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }
        String paramStr = StringUtils.join(list, "&");
        url = url + "?" + paramStr;
        return doPostToString(url, null, header);
    }

    public static HttpResponse doGetDefaultSecurity(String url, Map<String, String> header) throws Exception {
        return doGet(url, header);
    }

    public static StreamClosedHttpResponse doGetStringSecurity(String url, Map<String, String> header) throws Exception {
        return doGetForString(url, header);
    }

    public static String doGetToStringSecurity(String url, Map<String, String> header) throws Exception {
        return doGetToString(url, header, null);
    }

    public static String doGetToStringWithParamsSecurity(String url, Map<String, String> header, Map<String, Object> params) throws Exception {
        return doGetToString(url, header, params);
    }

    private static String doGetToString(String url, Map<String, String> header, Map<String, Object> params) throws Exception {
        HttpClient client = null;
        HttpGet get = null;
        String result = null;
        try {

            if (params != null && params.size() > 0) {
                StringBuffer paramsInUrl = new StringBuffer("?");
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    paramsInUrl.append(entry.getKey());
                    paramsInUrl.append("=");
                    paramsInUrl.append(entry.getValue());
                    paramsInUrl.append("&");
                }
                url = url + paramsInUrl.toString();
                url = url.substring(0, url.lastIndexOf("&"));
            }

            get = new HttpGet(url);

            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT);
            get.setConfig(customReqConf.build());
            HttpResponse res = null;

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (url.startsWith(DEFAULT_HTTPS)) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(get);
            } else {
                // 执行 Http 请求.
                client = HttpClientSSLUtil.client;
                res = client.execute(get);
            }
            if (res == null || res.getEntity() == null) {
                return null;
            }
            result = IOUtils.toString(res.getEntity().getContent(), DEFAULT_CHAR_SET);
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
            if (url.startsWith(DEFAULT_HTTPS) && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     * <p>
     * 支持HTTP、HTTPS POST请求  返回String类型
     */
    private static String doPostToString(String url, String jsonText, Map<String, String> header) throws Exception {

        HttpClient client = null;
        HttpPost post = new HttpPost(url);
        String result = null;
        try {
            if (jsonText != null && !jsonText.isEmpty()) {
                StringEntity entity = new StringEntity(jsonText, ContentType.APPLICATION_JSON);
                post.setEntity(entity);
            }

            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT);
            post.setConfig(customReqConf.build());
            HttpResponse res = null;

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (url.startsWith(DEFAULT_HTTPS)) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = HttpClientSSLUtil.client;
                res = client.execute(post);
            }
            if (res == null || res.getEntity() == null) {
                return null;
            }
            result = IOUtils.toString(res.getEntity().getContent(), DEFAULT_CHAR_SET);
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            post.releaseConnection();
            if (url.startsWith(DEFAULT_HTTPS) && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     * <p>
     * 支持HTTP、HTTPS POST请求
     */
    private static HttpResponse doPost(String url, String jsonText, Map<String, String> header) throws Exception {
        HttpClient client = null;
        HttpPost post = new HttpPost(url);

        try {
            if (jsonText != null && !jsonText.isEmpty()) {
                StringEntity entity = new StringEntity(jsonText, ContentType.APPLICATION_JSON);
                post.setEntity(entity);
            }

            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT);
            post.setConfig(customReqConf.build());
            HttpResponse res = null;

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (url.startsWith(DEFAULT_HTTPS)) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = HttpClientSSLUtil.client;
                res = client.execute(post);
            }
            if (res == null || res.getEntity() == null) {
                return null;
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            post.releaseConnection();
            if (url.startsWith(DEFAULT_HTTPS) && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     * <p>
     * 支持HTTP、HTTPS POST请求
     */
    private static StreamClosedHttpResponse doPostForString(String url, String jsonText, Map<String, String> header) throws Exception {
        HttpClient client = null;
        HttpPost post = new HttpPost(url);

        try {
            if (jsonText != null && !jsonText.isEmpty()) {
                StringEntity entity = new StringEntity(jsonText, ContentType.APPLICATION_JSON);
                post.setEntity(entity);
            }

            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT);
            post.setConfig(customReqConf.build());
            HttpResponse res = null;

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (url.startsWith(DEFAULT_HTTPS)) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = HttpClientSSLUtil.client;
                res = client.execute(post);
            }
            if (res == null || res.getEntity() == null) {
                return null;
            }
            return new StreamClosedHttpResponse(res);
        } catch (Exception e) {
            throw e;
        } finally {
            post.releaseConnection();
            if (url.startsWith(DEFAULT_HTTPS) && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     * <p>
     * 支持HTTP、HTTPS GET请求
     */
    private static HttpResponse doGet(String url, Map<String, String> header) throws Exception {
        HttpClient client = null;
        HttpGet get = new HttpGet(url);
        try {
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT);
            get.setConfig(customReqConf.build());
            HttpResponse res = null;

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (url.startsWith(DEFAULT_HTTPS)) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(get);
            } else {
                // 执行 Http 请求.
                client = HttpClientSSLUtil.client;
                res = client.execute(get);
            }
            if (res == null || res.getEntity() == null) {
                return null;
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            get.releaseConnection();
            if (url.startsWith(DEFAULT_HTTPS) && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     * <p>
     * 支持HTTP、HTTPS GET请求
     */
    private static StreamClosedHttpResponse doGetForString(String url, Map<String, String> header) throws Exception {
        HttpClient client = null;
        HttpGet get = new HttpGet(url);
        try {
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT);
            get.setConfig(customReqConf.build());
            HttpResponse res = null;

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (url.startsWith(DEFAULT_HTTPS)) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(get);
            } else {
                // 执行 Http 请求.
                client = HttpClientSSLUtil.client;
                res = client.execute(get);
            }
            if (res == null || res.getEntity() == null) {
                return null;
            }
            return new StreamClosedHttpResponse(res);
        } catch (Exception e) {
            throw e;
        } finally {
            get.releaseConnection();
            if (url.startsWith(DEFAULT_HTTPS) && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }

}
