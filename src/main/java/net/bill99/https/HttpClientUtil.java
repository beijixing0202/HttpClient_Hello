package net.bill99.https;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.testng.annotations.Test;

public class HttpClientUtil {

    public static String doGet(String url) throws Exception {
        //声明
        ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
        //加入相关的https请求方式
        Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
        //发送请求即可
        org.apache.commons.httpclient.HttpClient httpclient = new org.apache.commons.httpclient.HttpClient();
        GetMethod httpget = new GetMethod(url);
        System.out.println("===url===:" + url);
        try {
            httpclient.executeMethod(httpget);
            return httpget.getResponseBodyAsString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
            httpget.releaseConnection();
        }
    }
    
    public static String doPost(String url,NameValuePair[] params){
    	 //声明
        ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
        //加入相关的https请求方式
        Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
        //发送请求即可
        org.apache.commons.httpclient.HttpClient httpclient = new org.apache.commons.httpclient.HttpClient();
        PostMethod httpPost=new PostMethod(url);
        httpPost.setRequestBody(params);
        try {
			httpclient.executeMethod(httpPost);
			return httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			httpPost.releaseConnection();
		}
		return null;
		
    }
    
    @Test 
    public void test1(){
		String sendData="[{\"fid\":349,\"keyword\":\"issCode\",\"value\":\"90880019\"},{\"fid\":351,\"keyword\":\"backUrl\",\"value\":\"http://101.231.204.84:8091/sim/notify_url2.jsp\"},{\"fid\":370,\"keyword\":\"qrType\",\"value\":\"35\"},{\"fid\":387,\"keyword\":\"emvCodeIn\",\"value\":\"\"},{\"fid\":388,\"keyword\":\"reqAddnData\",\"value\":\"\"},{\"fid\":159,\"keyword\":\"accNo\",\"value\":\"6216261000000002485\"},{\"fid\":160,\"keyword\":\"name\",\"value\":\"宋小\"},{\"fid\":162,\"keyword\":\"cardAttr\",\"value\":\"01\"},{\"fid\":371,\"keyword\":\"acctClass\",\"value\":\"1\"}]";
    	String url="https://open.unionpay.com//ajweb/help/qrcodeFormPage/sendOk";
    	NameValuePair[] psotData={new NameValuePair("puid", "9"),new NameValuePair("requestType", "coverSweepReceiverApp"),
    	new NameValuePair("sendtype", "C2B码申请"),new NameValuePair("sendData", sendData)};
    	
    	System.out.println(doPost(url,psotData));
    }
      
}
