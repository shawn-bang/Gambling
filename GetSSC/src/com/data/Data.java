package com.data;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class Data {
	public static String getCQSSCData(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		// 设置为get取连接的方式.
		PostMethod post = new PostMethod(url);
		// 得到返回的response.
		client.executeMethod(post);
		String retResult = post.getResponseBodyAsString();
		return retResult;
	}
}
