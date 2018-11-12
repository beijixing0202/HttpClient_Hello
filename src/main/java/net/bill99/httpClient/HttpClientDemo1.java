package net.bill99.httpClient;

import java.io.IOException;

import net.bill99.https.HttpClientUtil;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.testng.annotations.Test;


public class HttpClientDemo1 {
	
	@Test
	public void test1(){
		String url="https://open.unionpay.com/ajweb/help/qrcodeFormPage/childPageDataLoad?puid=9";
		//HttpClientUtil client=new HttpClientUtil();
		try {
			String doGet = HttpClientUtil.doGet(url);
			System.out.println(doGet);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		String url="https://open.unionpay.com/ajweb/help/qrcodeFormPage/childPageDataLoad?puid=9";
		HttpClient client=new HttpClient();
		GetMethod getMethod=new GetMethod(url);
		try {
			client.executeMethod(getMethod);
			System.out.println("返回报文："+getMethod.getResponseBodyAsString());
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			getMethod.releaseConnection();
		}
		
		
	}
	
	@Test
	public void test3(){
		String url="https://open.unionpay.com/ajweb/help/qrcodeFormPage/sendOk";
		String sendData="[{\"fid\":349,\"keyword\":\"issCode\",\"value\":\"90880019\"},{\"fid\":351,\"keyword\":\"backUrl\",\"value\":\"http://101.231.204.84:8091/sim/notify_url2.jsp\"},{\"fid\":370,\"keyword\":\"qrType\",\"value\":\"35\"},{\"fid\":387,\"keyword\":\"emvCodeIn\",\"value\":\"\"},{\"fid\":388,\"keyword\":\"reqAddnData\",\"value\":\"\"},{\"fid\":159,\"keyword\":\"accNo\",\"value\":\"6216261000000002485\"},{\"fid\":160,\"keyword\":\"name\",\"value\":\"宋小\"},{\"fid\":162,\"keyword\":\"cardAttr\",\"value\":\"01\"},{\"fid\":371,\"keyword\":\"acctClass\",\"value\":\"1\"}]";
		HttpClient client=new HttpClient();
		PostMethod post=new PostMethod(url);
		NameValuePair[] params={new NameValuePair("puid", "9"),new NameValuePair("requestType", "coverSweepReceiverApp"),
				new NameValuePair("sendtype", "C2B码申请"),new NameValuePair("sendData", sendData)};
		post.setRequestBody(params);
		try {
			client.executeMethod(post);
			System.out.println(post.getResponseBodyAsString());
		 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}
