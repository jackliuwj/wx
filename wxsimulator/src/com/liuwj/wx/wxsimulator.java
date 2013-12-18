package com.liuwj.wx;

import java.io.IOException;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

public class wxsimulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
//				HttpStatus.SC_OK, "OK");
//		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
//		response.addHeader("Set-Cookie",
//				"c2=b; path=\"/\", c3=c; domain=\"localhost\"");
//		HeaderElementIterator it = new BasicHeaderElementIterator(
//				response.headerIterator("Set-Cookie"));
//		while (it.hasNext()) {
//			HeaderElement elem = it.nextElement();
//			System.out.println(elem.getName() + " = " + elem.getValue());
//			NameValuePair[] params = elem.getParameters();
//			for (int i = 0; i < params.length; i++) {
//				System.out.println(" " + params[i]);
//			}
//		}
		 DefaultHttpClient httpclient = new DefaultHttpClient();
//		String url = new String("https://api.weixin.qq.com/cgi-bin/user/info?access_token=HrVdWAQdhB9NbonGky5d8vR9iZgZVm_UyKfG20HzbxloQmta_4mj-Zy3uoSmAkDL8RSYjxpffmoS5BLXZQdaxfXgYNKCzgunPt08DsBt7Tkf4w9uQVT_TLniybuVXHA__eiYEyCztKP7Pcp33unaGA&openid=onoo7t_3W1BldvRuWua6YOB2GGAc");
		String url = new String("https://www.verisign.com");
		HttpGet httpget = new HttpGet(url);
//		GetMethod httpget1 = new GetMethod("https://www.verisign.com/"); 
		try {
			HttpResponse rsp = httpclient.execute(httpget);
			HttpEntity entity = rsp.getEntity();
			if(entity!=null){
				
				System.out.println(EntityUtils.toString(entity));
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
		}
		
		
		
	}

}
