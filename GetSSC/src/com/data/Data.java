package com.data;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class Data {
	public static String getCQSSCData(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		// ����Ϊgetȡ���ӵķ�ʽ.
		PostMethod post = new PostMethod(url);
		// �õ����ص�response.
		client.executeMethod(post);
		String retResult = post.getResponseBodyAsString();
		return retResult;
	}
}
