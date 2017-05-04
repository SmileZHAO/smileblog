package com.shxzhlxrr.util.socket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

import com.shxzhlxrr.util.string.StringUtil;

public class HttpClient {
	/**
	 * 主机
	 */
	private String host;

	private int port = 80;

	private String url;

	private String context;

	private String protocol;
	/**
	 * 客户端的信息
	 */
	private String userAgent;

	private InputStream contentInputStream;

	private byte[] contentData;

	private static Logger log = Logger.getLogger(HttpClient.class);

	private int status = 200;

	private Map<String, Object> header = new HashMap<String, Object>();

	public Map<String, Object> getHeader() {
		return header;
	}

	public HttpClient() {

	}

	public HttpClient(String host, int port, String url, String protocol) {
		this.host = host;
		this.port = port;
		this.url = url;
		this.protocol = protocol;
	}

	public HttpClient(String url) {
		parseHttpUrl(url);
	}

	public void parseHttpUrl(String url) {
		// TODO 校验url是否是正确的
		String protocol = "http";
		if (url.startsWith("https")) {
			protocol = "https";
		}

		url = url.substring(url.indexOf("//") + 2);
		String host = url;
		if (url.indexOf("/") < 0) {
			url = "/";
		} else {
			host = url.substring(0, url.indexOf("/"));
			url = url.substring(url.indexOf("/"));
		}

		int port = 80;
		if (host.contains(":")) {
			port = Integer.valueOf(host.substring(host.indexOf(":") + 1));
			host = host.substring(0, host.indexOf(":"));
		}

		this.host = host;
		this.port = port;
		this.url = url;
		this.protocol = protocol;
	}

	/**
	 * 获取返回的内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getContext() {

		return context;
	}

	/**
	 * 发送请求
	 */
	public void send() throws Exception {
		if (this.protocol == null) {
			this.protocol = "http";
		}

		validateParam();

		getHttpContent();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public InputStream getContentInputStream() {
		return contentInputStream;
	}

	public byte[] getContentData() {
		return contentData;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	private void getHttpContent() throws Exception {
		BufferedReader bf = null;
		PrintWriter pw = null;
		Socket soc = null;
		GZIPInputStream gzin = null;
		try {
			soc = new Socket(InetAddress.getByName(this.host), this.port);

			if (soc.isConnected()) {

				pw = new PrintWriter(soc.getOutputStream(), true);// 获取输出流

				sendReqMsg(pw);// 发送请求

				parseRes(soc);// 解析返回的请求数据

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
			if (soc != null) {
				try {
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (gzin != null) {
				gzin.close();
			}
		}
	}

	/**
	 * 解析返回的数据
	 */
	private void parseRes(Socket soc) throws IOException {
		long start = System.currentTimeMillis();
		InputStream in = soc.getInputStream();
		byte[] buf = new byte[1024 * 200];
		int isClose = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<String> titles = new ArrayList<String>();
		while ((isClose = in.read()) != -1) {
			baos.write(isClose);
			if ('\n' == isClose) {
				String str = new String(baos.toByteArray());
				titles.add(str);
				baos.reset();
				if (StringUtil.isNull(str)) {// 这个时候表示到了空行。
					parseHeader(titles);
					break;
				}
			}
		}
		long end = System.currentTimeMillis();
		log.debug("[解析图片的头]：" + ((end - start) / 1000));
		log.debug("[返回的消息的头]：" + this.header);
		if (200 != this.status) {
			throw new RuntimeException("访问连接出错,错误码:" + this.status);
		}

		if ("chunked".equals(this.header.get("Transfer-Encoding"))) {
			throw new RuntimeException("暂时不支持这种压缩的格式!");
		}
		int content_length = Integer.valueOf(String.valueOf(this.header.get("Content-Length")));
		int count = 0;
		int countNum = 0;
		while ((count = in.read(buf)) != -1) {
			baos.write(buf, 0, count);
			countNum = countNum + count;
			log.debug("[num]:" + countNum);
			end = System.currentTimeMillis();
			log.debug("[解析内容]：" + ((end - start) / 1000));
			if (content_length == countNum) {
				break;
			}
		}
		end = System.currentTimeMillis();
		log.debug("[解析消息的主体]：" + ((end - start) / 1000));
		if ("gzip".equals(this.header.get("Content-Encoding"))) {// 当是gzip压缩的时候，进行解压
			GZIPInputStream gzin = new GZIPInputStream(new ByteArrayInputStream(baos.toByteArray()));
			baos.reset();
			BufferedInputStream bufis = new BufferedInputStream(gzin);
			while ((count = bufis.read(buf)) != -1) {
				baos.write(buf);
			}
		}
		// String content_type =
		// String.valueOf(this.header.get("Content-Type"));
		// if (content_type.contains("text/html")) {
		this.context = new String(baos.toByteArray());
		this.contentData = baos.toByteArray();
		this.contentInputStream = new ByteArrayInputStream(this.contentData);
		// }
		end = System.currentTimeMillis();
		log.debug("[解析完毕]：" + ((end - start) / 1000));
	}

	/**
	 * 校验基本的数据不能为空
	 */
	private void validateParam() {
		assertNotNull("端口号不能为空", this.port);
		assertNotNull("主机不能为空", this.host);
		assertNotNull("访问的链接不能为空", this.url);
		assertNotNull("访问的协议不能为空", this.protocol);
		assertTrue("请设置协议为'http'或'https'", Pattern.compile("http[s]*").matcher(this.protocol).find());
	}

	private void sendReqMsg(PrintWriter pw) {
		pw.println("GET " + this.url + " HTTP/1.1");
		pw.println("Accept:text/html,image/webp,*/*;q=0.8");
		pw.println("Accept-Encoding:gzip, deflate, sdch, br");
		pw.println("Accept-Language:zh-CN,zh;q=0.8");
		pw.println("Connection:keep-alive");
		pw.println("Host:" + this.host + ":" + this.port);
		if (this.userAgent != null) {
			pw.println(this.userAgent);
		} else {
			this.userAgent = "User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
			pw.println(this.userAgent);
		}
		pw.println("\r\n");
	}

	/**
	 * 用来解析返回的数据的第一行
	 * 
	 * @param header
	 */
	private void parseHeader(List<String> titles) {
		String header = titles.get(0);
		String[] haaders = header.split(" ");
		this.header.put("protocol", haaders[0].trim());
		this.header.put("status", haaders[1].trim());
		this.status = Integer.valueOf(haaders[1].trim());
		titles.remove(0);
		for (String title : titles) {
			if (StringUtil.isNull(title)) {
				continue;
			}
			String key = title.substring(0, title.indexOf(":"));
			String value = title.substring(title.indexOf(":") + 1);
			this.header.put(key.trim(), value.trim());
		}
		log.debug(this.header);
	}

}
