package com.csair.csairmind.hunter.spider.downLoad;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.proxy.Proxy;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhangcheng
 */
public class HttpsClientGenerator extends HttpClientGenerator {

    private PoolingHttpClientConnectionManager connectionManager;

    public HttpsClientGenerator() {
        RegistryBuilder<ConnectionSocketFactory> builder = RegistryBuilder.create();
        Registry<ConnectionSocketFactory> reg = builder.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
        this.connectionManager = new PoolingHttpClientConnectionManager(reg);
        this.connectionManager.setDefaultMaxPerRoute(100);
    }

    public HttpClientGenerator setPoolSize(int poolSize) {
        this.connectionManager.setMaxTotal(poolSize);
        return this;
    }

    public CloseableHttpClient getClient(Site site, Proxy proxy) {
        return this.generateClient(site, proxy);
    }

    private CloseableHttpClient generateClient(Site site, Proxy proxy) {

        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        CredentialsProvider credsProvider = null;
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setSSLSocketFactory(sslsf);
        if(proxy != null && StringUtils.isNotBlank(proxy.getUser()) && StringUtils.isNotBlank(proxy.getPassword())) {
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(proxy.getHttpHost().getAddress().getHostAddress(), proxy.getHttpHost().getPort()), new UsernamePasswordCredentials(proxy.getUser(), proxy.getPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }

        if(site != null && site.getHttpProxy() != null && site.getUsernamePasswordCredentials() != null) {
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(site.getHttpProxy()), site.getUsernamePasswordCredentials());
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }

        httpClientBuilder.setConnectionManager(this.connectionManager);
        if(site != null && site.getUserAgent() != null) {
            httpClientBuilder.setUserAgent(site.getUserAgent());
        } else {
            httpClientBuilder.setUserAgent("");
        }

        if(site == null || site.isUseGzip()) {
            httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
                public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                    if(!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept-Encoding", "gzip");
                    }

                }
            });
        }

        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(site.getTimeOut()).setSoKeepAlive(true).setTcpNoDelay(true).build();
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        this.connectionManager.setDefaultSocketConfig(socketConfig);
        if(site != null) {
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(site.getRetryTimes(), true));
        }

        this.generateCookie(httpClientBuilder, site);
        return httpClientBuilder.build();
    }

    private void generateCookie(HttpClientBuilder httpClientBuilder, Site site) {
        CookieStore cookieStore = new BasicCookieStore();
        Iterator var4 = site.getCookies().entrySet().iterator();

        Map.Entry domainEntry;
        while(var4.hasNext()) {
            domainEntry = (Map.Entry)var4.next();
            BasicClientCookie cookie = new BasicClientCookie((String)domainEntry.getKey(), (String)domainEntry.getValue());
            cookie.setDomain(site.getDomain());
            cookieStore.addCookie(cookie);
        }

        var4 = site.getAllCookies().entrySet().iterator();

        while(var4.hasNext()) {
            domainEntry = (Map.Entry)var4.next();
            Iterator var9 = ((Map)domainEntry.getValue()).entrySet().iterator();

            while(var9.hasNext()) {
                Map.Entry<String, String> cookieEntry = (Map.Entry)var9.next();
                BasicClientCookie cookie = new BasicClientCookie((String)cookieEntry.getKey(), (String)cookieEntry.getValue());
                cookie.setDomain((String)domainEntry.getKey());
                cookieStore.addCookie(cookie);
            }
        }

        httpClientBuilder.setDefaultCookieStore(cookieStore);
    }

}
