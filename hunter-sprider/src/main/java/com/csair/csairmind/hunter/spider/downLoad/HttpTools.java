//package com.csair.csairmind.hunter.spider.downLoad;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TimeZone;
//import java.util.Map.Entry;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//import json.JsonUtils;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.NoHttpResponseException;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.AuthCache;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.CookieSpecs;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.conn.HttpHostConnectException;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.auth.BasicScheme;
//import org.apache.http.impl.client.BasicAuthCache;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.util.EntityUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import utils.Console;
//import utils.UserAgentUtils;
//
//@SuppressWarnings("deprecation")
//public class HttpTools {
//	private final Pattern patternForCharset = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");
//	private static String cookie = null;
//	private int proxyType = 1;// 0:ip+port代理，1:隧道代理
//	// 代理服务器
//	// private final Integer ProxyPort = 9020;
//	// private final String ProxyUser = "HF5SR836P4W5R08D";
//	// private final String ProxyPass = "D062CE385F502B3A";
//
//	// IP切换协议头
//	private final String ProxyHeadKey = "Proxy-Switch-Ip";
//	private final String ProxyHeadVal = "yes";
//
//	// private final boolean dyUseragent = true;// 是否用动态的useragent;
//
//	private List<Header> headers = null;
//
//	public static void main(String[] args) {
//
//	}
//
//	public static String httpPostData(String uri, String postData) {
//		int requestTimeOut = 10;
//		String retStr = "";
//		int tmout = 5;
//		if (requestTimeOut > 0) {
//			tmout = requestTimeOut;
//		}
//		try {
//			HttpParams httpParams = new BasicHttpParams();
//			// httpParams.setParameter("charset", "UTF-8");
//			HttpConnectionParams.setConnectionTimeout(httpParams, tmout * 1000); // 毫秒
//			HttpConnectionParams.setSoTimeout(httpParams, tmout * 1000);
//			HttpClient httpClient = new DefaultHttpClient(httpParams);
//
//			HttpPost httpPost = new HttpPost(uri);
//			StringEntity s = new StringEntity(postData.toString(), "UTF-8");
//			s.setContentType("application/json");
//			httpPost.setEntity(s);
//			HttpResponse response;
//			response = httpClient.execute(httpPost);
//			// 检验状态码，如果成功接收数据
//			int code = response.getStatusLine().getStatusCode();
//			if (code == 200) {
//				retStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//			}
//		} catch (ClientProtocolException e) {
//		} catch (IOException e) {
//		} catch (Exception e) {
//		}
//		return retStr;
//	}
//
//	@SuppressWarnings("deprecation")
//	public static String doPost(String url, String json) {
//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpPost post = new HttpPost(url);
//		String result = null;
//		try {
//			StringEntity s = new StringEntity(json.toString(), "UTF-8");
//			s.setContentType("application/json");
//			post.setEntity(s);
//			RequestConfig config = RequestConfig.custom()//
//					.setConnectTimeout(Console.CONNECT_TIMEOUT)//
//					.setConnectionRequestTimeout(Console.CONNECT_TIMEOUT)//
//					.setSocketTimeout(Console.SOCKET_TIMEOUT).build();//
//			post.setConfig(config);
//			HttpResponse res = client.execute(post);
//			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				result = EntityUtils.toString(res.getEntity());
//			}
//		} catch (Exception e) {
//
//		}
//		return result;
//	}
//
//	/**
//	 * 不用代理GET请求
//	 *
//	 * @param url
//	 *           请求URL
//	 * @param headerFileName
//	 *           请求头文件名
//	 * @param extraMap
//	 *           请求头的扩展
//	 * @param needCookie
//	 *           是否要cookie
//	 * @return 请求内容
//	 */
//	public String reqGet(String url, String headerFileName, Map<String, String> extraMap, boolean needCookie, boolean needProxy) {
//		return httpReqCommon(url, Console.HTTP_GET, null, headerFileName, extraMap, needProxy, needCookie);
//	}
//
//	public String reqPost(String url, Map<String, String> params, String headerFileName, Map<String, String> extraMap, boolean needCookie, boolean needProxy) {
//		return httpReqCommon(url, Console.HTTP_POST, params, headerFileName, extraMap, needProxy, needCookie);
//	}
//
//	private CloseableHttpResponse executeReq(CloseableHttpResponse response, CloseableHttpClient httpClient, HttpRequestBase request, boolean needProxy) {
//
//		try {
//			if (needProxy && proxyType == 1) {
//				HttpHost target = new HttpHost(Console.PROXY_HOST, Console.PROXY_PORT, "http");//
//				AuthCache authCache = new BasicAuthCache();
//				BasicScheme basicAuth = new BasicScheme();
//				authCache.put(target, basicAuth);
//
//				HttpClientContext localContext = HttpClientContext.create();
//				localContext.setAuthCache(authCache);
//				response = httpClient.execute(target, request, localContext);
//			} else {
//				response = httpClient.execute(request);
//			}
//		} catch (ConnectTimeoutException e) {// 代理不可用
//			System.out.println("连接超时");
//		} catch (HttpHostConnectException e2) {
//			System.out.println("主机连接异常");
//		} catch (NoHttpResponseException e3) {
//			e3.printStackTrace();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		if (response != null) {
//			System.out.println("响应码:" + response.getStatusLine().getStatusCode());
//		}
//		return response;
//	}
//
//	// 使用隧道代理，有用户名和密码
//	private CloseableHttpClient getAuthPorxyClient(String fileName, Map<String, String> extraHeaderMap, boolean needCookie) {
//		if (extraHeaderMap == null) {
//			extraHeaderMap = new HashMap<String, String>();
//		}
//		extraHeaderMap.put(ProxyHeadKey, ProxyHeadVal);
//		List<Header> list = getHeaders(fileName, extraHeaderMap, needCookie);
//
//		HttpHost target = new HttpHost(Console.PROXY_HOST, Console.PROXY_PORT, "http");
//
//		CredentialsProvider credsProvider = new BasicCredentialsProvider();
//		credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()), new UsernamePasswordCredentials(Console.PROXY_USER, Console.PROXY_PASS));
//
//		return HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setDefaultHeaders(list).build();
//
//	}
//
//	// 常规代理，指定IP和端口
//	private CloseableHttpClient getProxyClient(HttpRequestBase req, String fileName, Map<String, String> extraHeaderMap, boolean needCookie) {
//
//		// 模似请求头
//		List<Header> list = getHeaders(fileName, extraHeaderMap, needCookie);
//
//		Header[] _list = new Header[list.size()];
//		for (int i = 0; i < list.size(); i++) {
//			_list[i] = list.get(i);
//		}
//		req.setHeaders(_list);
//
//		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//		// HttpClient
//		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//
//		HttpHost proxy = ProxyUtils.getProxyFromPool();
//		RequestConfig config = null;
//		if (proxy != null) {
//			config = RequestConfig.custom()//
//					.setProxy(proxy)//
//					.setSocketTimeout(Console.SOCKET_TIMEOUT)//
//					.setConnectTimeout(Console.CONNECT_TIMEOUT)//
//					.setCookieSpec(CookieSpecs.STANDARD)//
//					.build();
//		} else {
//			config = RequestConfig.custom()//
//					.setSocketTimeout(Console.SOCKET_TIMEOUT)//
//					.setConnectTimeout(Console.CONNECT_TIMEOUT)//
//					.setCookieSpec(CookieSpecs.STANDARD)//
//					.build();
//		}
//		req.setConfig(config);
//		return closeableHttpClient;
//	}
//
//	public CloseableHttpClient getHttpsClient(HttpRequestBase req, String fileName, Map<String, String> extraHeaderMap, boolean needCookie) {
//
//		SSLContext sslcontext = createSSLContext();
//
//		// 模似请求头
//		List<Header> list = getHeaders(fileName, extraHeaderMap, needCookie);
//
//		Header[] _list = new Header[list.size()];
//		for (int i = 0; i < list.size(); i++) {
//			_list[i] = list.get(i);
//		}
//		req.setHeaders(_list);
//
//		RequestConfig config = RequestConfig.custom()//
//				// .setSocketTimeout(Console.SOCKET_TIMEOUT)//
//				// .setConnectTimeout(Console.CONNECT_TIMEOUT)//
//				// .setCookieSpec(CookieSpecs.STANDARD)//
//				.build();
//
//		req.setConfig(config);
//
//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new HostnameVerifier() {
//
//			public boolean verify(String paramString, SSLSession paramSSLSession) {
//				return true;
//			}
//		});
//		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//		return httpclient;
//	}
//
//	// 不使用代理
//	private CloseableHttpClient getClient(HttpRequestBase req, String fileName, Map<String, String> extraHeaderMap, boolean needCookie) {
//
//		// 模似请求头
//		List<Header> list = getHeaders(fileName, extraHeaderMap, needCookie);
//
//		Header[] _list = new Header[list.size()];
//		for (int i = 0; i < list.size(); i++) {
//			_list[i] = list.get(i);
//		}
//		req.setHeaders(_list);
//
//		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//		// HttpClient
//		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//
//		RequestConfig config = RequestConfig.custom()//
//				.setSocketTimeout(Console.SOCKET_TIMEOUT)//
//				.setConnectTimeout(Console.CONNECT_TIMEOUT)//
//				.setCookieSpec(CookieSpecs.STANDARD)//
//				.build();
//
//		req.setConfig(config);
//		return closeableHttpClient;
//	}
//
//	// 下载文件时
//	public InputStream reqCommonStream(HttpRequestBase req, String url, String headerName, Map<String, String> extraHeaderMap, boolean useProxy, boolean needCookie) {
//		InputStream is = null;
//		CloseableHttpResponse response = null;
//		CloseableHttpClient httpClient = null;
//		if (useProxy) {
//			if (proxyType == 0) {
//				httpClient = getProxyClient(req, headerName, extraHeaderMap, needCookie);
//			} else {
//				httpClient = getAuthPorxyClient(headerName, extraHeaderMap, needCookie);
//			}
//		} else {
//			if (url.startsWith("https")) {
//				httpClient = getHttpsClient(req, headerName, extraHeaderMap, needCookie);
//			} else {
//				httpClient = getClient(req, headerName, extraHeaderMap, needCookie);
//			}
//
//		}
//		response = executeReq(response, httpClient, req, useProxy);
//
//		if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//			try {
//				HttpEntity entity = response.getEntity();
//				is = entity.getContent();
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//			}
//		}
//		return is;
//	}
//
//	public File downloadImg(String url, String headerName, Map<String, String> extraHeaderMap, boolean useProxy, boolean needCookie) {
//		File file = new File("c:/img_" + Console.getNowExp() + ".jpg");
//		HttpGet req = new HttpGet(url);
//		InputStream is = null;
//		FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream(file);
//			is = reqCommonStream(req, url, headerName, extraHeaderMap, useProxy, needCookie);
//			if (is != null) {
//				byte[] contentBytes = null;
//				try {
//					contentBytes = IOUtils.toByteArray(is);
//				} catch (Exception e1) {
//					// e1.printStackTrace();
//				}
//				fos.write(contentBytes);
//				fos.flush();
//				fos.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return file;
//
//	}
//
//	private String reqCommon(HttpRequestBase req, String url, String headerName, Map<String, String> extraHeaderMap, boolean useProxy, boolean needCookie) {
//		long start = new Date().getTime();
//		InputStream is = null;
//		CloseableHttpResponse response = null;
//		CloseableHttpClient httpClient = null;
//		if (useProxy) {// 需要代理
//			if (proxyType == 0) {
//				httpClient = getProxyClient(req, headerName, extraHeaderMap, needCookie);
//			} else {
//				httpClient = getAuthPorxyClient(headerName, extraHeaderMap, needCookie);
//			}
//		} else {
//			if (url.startsWith("https")) {
//				httpClient = getHttpsClient(req, headerName, extraHeaderMap, needCookie);
//			} else {
//
//				httpClient = getClient(req, headerName, extraHeaderMap, needCookie);
//			}
//
//		}
//
//		response = executeReq(response, httpClient, req, useProxy);
//
//		if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//			try {
//				HttpEntity entity = response.getEntity();
//				is = entity.getContent();
//				byte[] contentBytes = IOUtils.toByteArray(is);
//				String htmlCharset = getPageCharset(response, contentBytes);// 获取页面的编码信息
//				long end = new Date().getTime();
//				long tome = end - start;
//				String res = new String(contentBytes, htmlCharset);
//				if (res == null) {
//					res = "";
//				}
//				System.out.println("请求耗时:" + tome + "ms" + "\t返回长度:" + res.length());
//
//				return res;
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//
//				closeStream(is, response, httpClient, req);
//
//			}
//		}
//		return "";
//	}
//
//	private String httpReqCommon(String url, String method, Map<String, String> params, String headerName, Map<String, String> extraMap, boolean needProxy, boolean needCookie) {
//		if (!url.startsWith("http://") && !url.startsWith("https://")) {
//			url = "http://" + url;
//		}
//		if (Console.HTTP_GET.equalsIgnoreCase(method)) {
//			HttpGet httpGet = new HttpGet(url);
//			return reqCommon(httpGet, url, headerName, extraMap, needProxy, needCookie);
//		} else if (Console.HTTP_POST.equalsIgnoreCase(method)) {
//			List<NameValuePair> list = new ArrayList<NameValuePair>();
//			if (params != null && !params.isEmpty()) {
//				for (Map.Entry<String, String> entry : params.entrySet()) {
//					list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//				}
//			}
//			HttpPost httpPost = new HttpPost(url);
//			try {
//				if (extraMap != null && params != null && extraMap.get("Content-Type") != null) {
//					if (extraMap.get("Content-Type").equalsIgnoreCase("application/json")) {
//						StringEntity s = new StringEntity(JsonUtils.toJSon(params));
//						s.setContentEncoding("utf-8");
//						s.setContentType("application/json");
//						httpPost.setEntity(s);
//					}
//				} else {
//					httpPost.setEntity(new UrlEncodedFormEntity(list, Console.DEFAULT_CHARSET));
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return reqCommon(httpPost, url, headerName, extraMap, needProxy, needCookie);
//		}
//
//		return null;
//
//	}
//
//	private void closeStream(InputStream is, CloseableHttpResponse response, CloseableHttpClient httpClient, HttpRequestBase req) {
//		if (is != null) {
//			try {
//				is.close();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//		if (response != null) {
//			try {
//				response.close();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//		if (httpClient != null) {
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (req != null) {
//			req.abort();
//		}
//	}
//
//	private List<Header> getHeaders(String fileName, Map<String, String> extraHeaderMap, boolean needCookie) {
//		String randomUserAgent = UserAgentUtils.getRandomUserAgent();
//
//		BasicHeader userAgentHeader = new BasicHeader("User-Agent", randomUserAgent);// 动态的，每次更新
//		if (headers == null) {
//			headers = HeaderUtils.getHeaderList(fileName, null);// 一般是固定的header，从配置文件里读取
//			// 自定义的header，可传动态的值
//			if (extraHeaderMap != null && !extraHeaderMap.isEmpty()) {
//				Set<Entry<String, String>> entrySet = extraHeaderMap.entrySet();
//				for (Entry<String, String> entry : entrySet) {
//					headers.add(new BasicHeader(entry.getKey(), entry.getValue()));
//				}
//			}
//		}
//
//		headers.add(userAgentHeader);
//
//		if (needCookie) {
//			if (cookie == null) {
//				cookie = CookieUtils.getCookie(true);
//			}
//			headers.add(new BasicHeader("Cookie", cookie));
//		}
//		if (headers.size() == 1)
//			System.out.println("请求头为空");
//		return headers;
//
//	}
//
//	/**
//	 * 通过页面源码获取网页的编码信息
//	 *
//	 * @param httpResponse
//	 * @param contentBytes
//	 * @return
//	 * @throws IOException
//	 */
//	private String getPageCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
//		String charset;
//		String value = httpResponse.getEntity().getContentType().getValue();
//		charset = getCharset(value);
//		if (StringUtils.isNotBlank(charset)) {
//			return charset;
//		}
//		Charset defaultCharset = Charset.defaultCharset();
//		String content = new String(contentBytes, defaultCharset.name());
//		if (StringUtils.isNotEmpty(content)) {
//			Document document = Jsoup.parse(content);
//			Elements links = document.select("meta");
//			for (Element link : links) {
//				String metaContent = link.attr("content");
//				String metaCharset = link.attr("charset");
//				if (metaContent.indexOf("charset") != -1) {
//					metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
//					charset = metaContent.split("=")[1];
//					break;
//				} else if (StringUtils.isNotEmpty(metaCharset)) {
//					charset = metaCharset;
//					break;
//				}
//			}
//		}
//		if (StringUtils.isBlank(charset)) {
//			charset = Console.DEFAULT_CHARSET;
//		}
//
//		List<String> charsetList = new ArrayList<String>();
//		charsetList.add("gbk");
//		charsetList.add("gb2312");
//		charsetList.add("utf-8");
//		charsetList.add("iso-8859-1");
//		if (!charsetList.contains(charset.toLowerCase())) {// 如果解析网页的编码信息在不列表里，则默认为GBK
//			charset = Console.DEFAULT_CHARSET;
//		}
//		return charset;
//	}
//
//	private static SSLContext createSSLContext() {
//		SSLContext sslcontext = null;
//		try {
//			sslcontext = SSLContext.getInstance("TLS");
//			sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (KeyManagementException e) {
//			e.printStackTrace();
//		}
//		return sslcontext;
//	}
//
//	private static class TrustAnyTrustManager implements X509TrustManager {
//
//		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//		}
//
//		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//		}
//
//		public X509Certificate[] getAcceptedIssuers() {
//			return new X509Certificate[] {};
//		}
//	}
//
//	// 判断访问目标网站是否需要代理
//	private boolean testNeedProxy(String targetUrl) {
//		boolean result = true;
//		URL url;
//		try {
//			url = new URL(targetUrl);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setConnectTimeout(Console.CONNECT_TIMEOUT);
//			int i = connection.getContentLength();
//			if (i > 0) {
//				result = false;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	public void jsoupReq() {
//		// 定义申请获得的appKey和appSecret
//		String appkey = "XXXXXXXX";
//		String secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
//
//		// 创建参数表
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("app_key", appkey);
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 使用中国时间，以免时区不同导致认证错误
//		paramMap.put("timestamp", format.format(new Date()));
//
//		// 对参数名进行排序
//		String[] keyArray = paramMap.keySet().toArray(new String[0]);
//		Arrays.sort(keyArray);
//
//		// 拼接有序的参数名-值串
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append(secret);
//		for (String key : keyArray) {
//			stringBuilder.append(key).append(paramMap.get(key));
//		}
//
//		stringBuilder.append(secret);
//		String codes = stringBuilder.toString();
//
//		// MD5编码并转为大写， 这里使用的是Apache codec
//		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(codes).toUpperCase();
//
//		paramMap.put("sign", sign);
//
//		// 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
//		// String authHeader = "MYH-AUTH-MD5 " + Joiner.on('&').withKeyValueSeparator("=").join(paramMap);
//
//		// System.out.println(authHeader);
//
//		// 接下来使用蚂蚁动态代理进行访问
//
//		// 这里以jsoup为例，将authHeader放入请求头中即可
//		// 原版的jsoup设置代理不方便，这里使用E-lai提供的修改版(https://github.com/E-lai/jsoup-proxy)
//		// 注意authHeader必须在每次请求时都重新计算，要不然会因为时间误差而认证失败
//		// Document doc = Jsoup.connect("http://1212.ip138.com/ic.asp").proxy("test.proxy.mayidaili.com", 8123, null).header("Proxy-Authorization", authHeader).get();
//
//	}
//
//	private String getCharset(String contentType) {
//		Matcher matcher = patternForCharset.matcher(contentType);
//		if (matcher.find()) {
//			String charset = matcher.group(1);
//			if (Charset.isSupported(charset)) {
//				return charset;
//			}
//		}
//		return null;
//	}
//
//}
