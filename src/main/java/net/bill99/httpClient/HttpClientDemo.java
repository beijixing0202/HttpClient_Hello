package net.bill99.httpClient;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class HttpClientDemo {
	
	@Test
	public void test2(){
		
			HttpClient client=new HttpClient();
			String url="http://192.168.8.112/guide/aggregatePay";
			client.getParams().setParameter(HttpMethodParams.CREDENTIAL_CHARSET, "utf-8");
			PostMethod postPay=new PostMethod(url);
			postPay.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			postPay.setRequestBody(ResquestBody(map1()));
			try {
				client.executeMethod(postPay);
				String response=postPay.getResponseBodyAsString();
				System.out.println(response);
			} catch (Exception e) {
				
				e.printStackTrace();
			} 	
	}
	
	
	
	@Test
	public void test1(){
		String loginUrl="http://192.168.14.88:8088/cap-mock/loginServlet";
		HttpClient client=new HttpClient();
		client.getParams().setParameter(HttpMethodParams.CREDENTIAL_CHARSET, "utf-8");
		PostMethod post=new PostMethod(loginUrl);
		
		NameValuePair[] postData={new NameValuePair("username", "admin"),new NameValuePair("password","ateMock")};
		
		post.setRequestBody(postData);
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		try{
			client.executeMethod(post);
			//System.out.println(post.getResponseBodyAsString());
			Document document=Jsoup.parse(post.getResponseBodyAsString());
			if(document.title().equals("ATE test page")){
				
				System.out.println("登录成功");
			}
			Cookie[] cookies=client.getState().getCookies();
			StringBuffer stringBuffer=new StringBuffer();
			for(Cookie c:cookies){
				stringBuffer.append(c);
			}
			System.out.println("Cookie:"+stringBuffer);
			
			String url="http://192.168.14.88:8088/cap-mock/orderMdpBankProcess.jsp";
			PostMethod postPay=new PostMethod(url);
			postPay.setRequestHeader("Cookie",stringBuffer.toString());			
			postPay.setRequestBody(ResquestBody(map()));
			client.executeMethod(postPay);
			String response=postPay.getResponseBodyAsString();
			//System.out.println(response);
			System.out.println(sliceResponse(response));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static NameValuePair[] ResquestBody(Map<String, String> params){
		if(params!=null){
			Object[] keys=params.keySet().toArray();
			NameValuePair[] pairs=new NameValuePair[keys.length];
			for(int i=0;i<keys.length;i++){
				String key=(String) keys[i];
				pairs[i]=new NameValuePair(key, params.get(key));
			}
			
			return pairs;
		}else{
			return null;
		}
		
	}
	
	private static Map<String, String> sliceResponse(String response) {
        Map<String, String> responseData = new HashMap<String, String>();
        String regEx="errorInfo\":\"(.*?)\".*?billOrderNo\":\"(.*?)\".*?tradeId\":\"(.*?)\".*?outTradeNo\":\"(.*?)\".*?数据库实例为(.*?) 订单表为.*?流水表为 (.*?)<";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            String errorInfo = matcher.group(1);
            if (errorInfo.equals("成功")) {
            	System.out.println("交易成功");
                responseData.put("billOrderNo", matcher.group(2));
                responseData.put("tradeId", matcher.group(3));
                responseData.put("outTradeNo", matcher.group(4));
                responseData.put("数据库实例", matcher.group(5));
                responseData.put("流水表", matcher.group(6));
                
            } else {
                System.out.println("ATE 3.0绑卡交易请求失败,请检查.");
            }
        }
        Reporter.log(responseData.toString());
        return responseData;
    }	

	public static Map<String, String> map(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("functionCode", "10");
		map.put("hessianUrl", "");
		map.put("sendcont", "");
		map.put("appId", "101");
		map.put("merchantCode", "10011478572");
		map.put("outTradeNo", "");
		map.put("channelType", "25");
		map.put("orderType", "250004");
		map.put("outOrderType", "10");
		map.put("orderAmount", "1000");
		map.put("payAmount", "1000");
		map.put("payMedia", "");
		map.put("equityFlag", "0");
		map.put("equityCode", "");
		map.put("equityAmount", "");
		map.put("equityDesc", "");
		map.put("preferentialProp", "");
		map.put("stlMerchantCode", "10013527714");
		map.put("pinNum", "");
		map.put("payMode", "11");
		map.put("bankAcctId", "6225882112365478");
		map.put("bankAcctType", "0002");
		map.put("bankAcctName", "");
		map.put("billOrderId", "");
		map.put("origMerOrderId", "");
		map.put("merchantId", "");
		map.put("txnType", "");
		map.put("initiativeFlag", "");
		map.put("secureType", "");
		map.put("rtmsValidateElement", "");
		map.put("sysVersion", "");
		map.put("token", "");
		map.put("validCode", "");
		map.put("currencyCode", "");
		map.put("txnTimeStart", "");
		map.put("txnTimeExpire", "");
		map.put("authCode", "");
		map.put("memberCode", "10013527714");
		map.put("memberMark", "");
		map.put("productTag", "");
		map.put("productDesc", "");
		map.put("orderCount", "");
		map.put("subMerchantId", "");
		map.put("subMerchantName", "");
		map.put("memberBankId", "");
		map.put("txnSendIp", "");
		map.put("subTerminalId", "");
		map.put("deviceInfo", "");
		map.put("outEquityCode", "");
		map.put("outEquityAmount", "");
		map.put("memo", "");
		map.put("bgUrl", "");
		map.put("notifyMode", "");
		map.put("pageUrl", "");
		map.put("inputCharset", "1");
		map.put("gpsInfo", "");
		map.put("rtmsValidateElement", "");
		map.put("sysVersion", "");
		map.put("rtmsJson", "");
		map.put("stageInfo", "");
		//map.put("ext2", "");
		//map.put("txnMemberCode", "");
		return map;
	}
	
	public static Map<String, String> map1(){
		Map<String, String> map=new HashMap<String, String>();
		
		map.put("merchantId", "812011145110001");
		map.put("terminalId", "55555527");
		map.put("txnType", "PUR");
		map.put("txnMode", "0");
		map.put("tradeSys", "2");
		map.put("settleMerchantId", "812011145110001");
		map.put("termTraceNo", "15414723529562");
		map.put("appType", "CUPBSC");
		map.put("amt", "100");
		map.put("cur", "CNY");
		map.put("externalTraceNo", "15414723529562");
		map.put("termTxnTime", "2018-01-10");
		map.put("dimensionCode", "6227529575265321198");
		return map;
	}
	@Test
	public void test3(){
		String url="http://192.168.8.112/guide/aggregatePay";
		HttpClient client=new HttpClient();
		PostMethod postPay=new PostMethod(url);
		postPay.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
		postPay.setRequestBody(map2());
		try {
			client.executeMethod(postPay);
			String response=postPay.getResponseBodyAsString();
			System.out.println(response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}
	public static String map2(){
		Map<String,String> jsonMap =new  HashMap<String,String>();
		jsonMap.put("merchantId", "812011145110001");
		jsonMap.put("terminalId", "55555527");
		jsonMap.put("txnType", "PUR");
		jsonMap.put("txnMode", "0");
		jsonMap.put("tradeSys", "2");
		jsonMap.put("serviceChannelType", "S");
		jsonMap.put("serviceChannelSrc", "H");
		jsonMap.put("settleMerchantId", "812011145110001");
		jsonMap.put("termTraceNo", "1511015125135");
		jsonMap.put("appType", "CUPBSC");
		jsonMap.put("amt", "100");
		jsonMap.put("cur", "CNY");
		jsonMap.put("externalTraceNo", "1511015125135");
		jsonMap.put("termTxnTime", "2018-01-10");
		jsonMap.put("dimensionCode", "6224354170939083318");
	    String json = new JSONObject(jsonMap).toString();
	    System.out.println(json);
		return json;
	
		}
	
	
}
