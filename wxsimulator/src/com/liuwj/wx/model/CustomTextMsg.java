package com.liuwj.wx.model;

import com.google.gson.Gson;

/**
 * @author liuwj
 *
 */
public class CustomTextMsg {

	public String getTouser() {
		return touser;
	}



	public void setTouser(String touser) {
		this.touser = touser;
	}



	public String getMsgtype() {
		return msgtype;
	}



	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}



	public Text getText() {
		return text;
	}



	public void setText(Text text) {
		this.text = text;
	}



	private String touser;
	
	private String msgtype;
	
	private Text text;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CustomTextMsg cTextMsg = new CustomTextMsg();
		cTextMsg.setTouser("user");
		cTextMsg.setMsgtype("text");
		Text t = new Text();
		t.setContent("123");
		cTextMsg.setText(t);
		Gson g = new Gson();
		String s = g.toJson(cTextMsg);
		System.out.println(s);
		
		
	}

}
