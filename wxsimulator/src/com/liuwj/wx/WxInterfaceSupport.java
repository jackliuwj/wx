package com.liuwj.wx;

import java.io.File;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.liuwj.wx.model.AcessToken;
import com.liuwj.wx.model.CustomTextMsg;
import com.liuwj.wx.model.Text;
import com.liuwj.wx.model.UserInfo;

public class WxInterfaceSupport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WxInterfaceSupport wx = new WxInterfaceSupport();
		// 爱上旅行APPID和APPSECRET
		// String token = wx.getAcessToken("wx7af6701c45f83ece",
		// "a01cb022f8c072a14848dcbcf7a051b9");
		//测试账号jackliu openid
		String openid = "onoo7t_3W1BldvRuWua6YOB2GGAc";
		// 测试账号
		String token = wx.getAcessToken("wx9a4a5f974a9bde64",
				"c6b23dbc25697b036095c09fd9e1f763");
		//发送默认消息123
//		wx.sendCustomMsg(token, openid);
		
//		//获取用户信息
//		String nickname = wx.getUserInfo(token, openid);
//		System.out.println(nickname);
		
		String fileName = "D:\\test.jpg";
		String mediaid = wx.uploadFile(token, fileName, "image");
		

	}

	public String getAcessToken(String appid, String appsecret) {
		String baseReqUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
		String reqUrl = baseReqUrl + "&appid=" + appid + "&secret=" + appsecret;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		HttpGet httpget = new HttpGet(reqUrl);
		try {
			HttpResponse rsp = httpclient.execute(httpget);
			HttpEntity entity = rsp.getEntity();
			if (entity != null) {
				String token = EntityUtils.toString(entity);
				Gson gson = new Gson();
				AcessToken acessToken = gson.fromJson(token, AcessToken.class);
				System.out.println(acessToken.getAccess_token());
				return acessToken.getAccess_token();
			}

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			// System.err.println();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}

		return "-1";
	}

	public void sendCustomMsg(String token, String openid) {
		// TODO Auto-generated method stub
		// 构造模拟工具
		String baseReqUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
		String reqUrl = baseReqUrl + token;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		HttpPost httpPost = new HttpPost(reqUrl);

		CustomTextMsg cTextMsg = new CustomTextMsg();
		cTextMsg.setTouser(openid);
		cTextMsg.setMsgtype("text");
		Text t = new Text();
		t.setContent("123");
		cTextMsg.setText(t);
		Gson g = new Gson();
		String s = g.toJson(cTextMsg);
		System.out.println(s);

		try {
			HttpEntity entity = new StringEntity(s);
			httpPost.setEntity(entity);
			for (int i = 0; i < 5; i++) {
				HttpResponse rsp = httpclient.execute(httpPost);
				HttpEntity entity2 = rsp.getEntity();
				if (entity2 != null) {
					String rst = EntityUtils.toString(entity2);
					System.out.println(rst);
				}
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}

	}
	
	public String getUserInfo(String token ,String openid){
		String baseReqUrl = "https://api.weixin.qq.com/cgi-bin/user/info?";
		String reqUrl = baseReqUrl + "access_token=" + token + "&openid=" + openid;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				"UTF-8");
//		httpclient.getParams().setParameter(HttpMethodParams., arg1)
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		HttpGet httpget = new HttpGet(reqUrl);
		try {
			HttpResponse rsp = httpclient.execute(httpget);
			HttpEntity entity = rsp.getEntity();
			if (entity != null) {
				String userInfo = EntityUtils.toString(entity,"UTF-8");
				System.out.println(userInfo);

				Gson gson = new Gson();
				UserInfo userInfo1 = gson.fromJson(userInfo, UserInfo.class);
				return userInfo1.getNickname();
			}

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			// System.err.println();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}

		return "-1";
		
	}
	/**
	 * 上传文件到微信
	 * @param fileName
	 * @return
	 */
	public String uploadFile(String token,String fileName,String type){
		
		HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httppost = new HttpPost("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=" +
            		token +
            		"&type=" +
            		type);

            FileBody bin = new FileBody(new File(fileName));
            StringBody comment = new StringBody("A binary file of some kind");

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("bin", bin);
            reqEntity.addPart("comment", comment);

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            System.out.println(httppost.toString());
            Header[] headers= httppost.getAllHeaders();
            System.out.println(headers.length);
            for(int i=0;i<headers.length;i++){
            	System.out.println(headers[i].getName());
            	System.out.println(headers[i].getValue());
                System.out.println("----------------------------------------");

            	
            }
            

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            String rst = EntityUtils.toString(resEntity);
            System.out.println(rst);
            return rst;
        }catch(Exception e){
        	
        	e.printStackTrace();
        } 
        finally {
            try { httpclient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
        
        return "-1";
		
		
	}
	
}
