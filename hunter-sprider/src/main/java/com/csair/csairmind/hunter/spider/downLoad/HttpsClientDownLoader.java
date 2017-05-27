package com.csair.csairmind.hunter.spider.downLoad;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.utils.WMCollections;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangcheng
 */
@Slf4j
public class HttpsClientDownLoader extends HttpClientDownloader {

    private HttpsClientGenerator httpClientGenerator = new HttpsClientGenerator();

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    private CloseableHttpClient getHttpClient(Site site, Proxy proxy) {
        CloseableHttpClient httpClient;
        synchronized (this) {
            httpClient = this.createSSLClientDefault();
        }
        return httpClient;
    }

    @Override
    public Page download(Request request, Task task) {
        Site site = null;
        if (task != null) {
            site = task.getSite();
        }

        String charset = null;
        Map<String, String> headers = null;
        Set acceptStatCode;
        if (site != null) {
            acceptStatCode = site.getAcceptStatCode();
            charset = site.getCharset();
            headers = site.getHeaders();
        } else {
            acceptStatCode = WMCollections.newHashSet(new Integer[]{Integer.valueOf(200)});
        }

        log.info("downloading page {}", request.getUrl());
        CloseableHttpResponse httpResponse = null;
        int statusCode = 0;

        Page var10;
        try {
            HttpHost proxyHost = null;
            Proxy proxy = null;
            if (site.getHttpProxyPool() != null && site.getHttpProxyPool().isEnable()) {
                proxy = site.getHttpProxyFromPool();
                proxyHost = proxy.getHttpHost();
            } else if (site.getHttpProxy() != null) {
                proxyHost = site.getHttpProxy();
            }

            HttpUriRequest httpUriRequest = this.getHttpUriRequest(request, site, headers, proxyHost);
            httpResponse = this.getHttpClient(site, proxy).execute(httpUriRequest);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            request.putExtra("statusCode", Integer.valueOf(statusCode));
            Page page;
            if (!this.statusAccept(acceptStatCode, statusCode)) {
                log.warn("get page {} error, status code {} ", request.getUrl(), Integer.valueOf(statusCode));
                page = null;
                return page;
            }

            page = this.handleResponse(request, charset, httpResponse, task);
            this.onSuccess(request);
            Page var13 = page;
            return var13;
        } catch (IOException var25) {
            log.warn("download page {} error", request.getUrl(), var25);
            if (site.getCycleRetryTimes() > 0) {
                var10 = this.addToCycleRetry(request, site);
                return var10;
            }

            this.onError(request);
            var10 = null;
        } finally {
            request.putExtra("statusCode", Integer.valueOf(statusCode));
            if (site.getHttpProxyPool() != null && site.getHttpProxyPool().isEnable()) {
                site.returnHttpProxyToPool((HttpHost) request.getExtra("proxy"), ((Integer) request.getExtra("statusCode")).intValue());
            }

            try {
                if (httpResponse != null) {
                    EntityUtils.consume(httpResponse.getEntity());
                }
            } catch (IOException var24) {
                log.warn("close response fail", var24);
            }

        }
        return var10;
    }
}
