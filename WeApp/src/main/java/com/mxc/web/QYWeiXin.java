package com.mxc.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.GlobalPara;
import com.google.gson.JsonObject;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

@Controller
public class QYWeiXin {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public QYWeiXin() {

	}

	
	/*
	 * 连接企业公众号  eg:http://o162n94995.51mypc.cn/WeApp/linkQYWeiXin
	 * */
	@RequestMapping(value="/linkQYWeiXin",method = RequestMethod.GET)
	protected void linkQYWx(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			String sToken = "84utqWmU7u0SB4n9v";//这个Token是随机生成，但是必须跟企业号上的相同
		    String sCorpID = "wxcf92c01160189ac4";//这里是你企业号的CorpID
		    String sEncodingAESKey = "oFK4YwkaizV4hT6YMx502A87UebNRCMq7kAFva01Frl";
			// 微信加密签名 
	        String sVerifyMsgSig = req.getParameter("msg_signature");
	        // 时间戳
	        String sVerifyTimeStamp = req.getParameter("timestamp");
	        // 随机数
	        String sVerifyNonce = req.getParameter("nonce");
	        // 随机字符串
	        String sVerifyEchoStr = req.getParameter("echostr");
	        String sEchoStr; //需要返回的明文       
			PrintWriter out = resp.getWriter();
			WXBizMsgCrypt wxcpt;
			try{
				wxcpt=new WXBizMsgCrypt(sToken,sEncodingAESKey,sCorpID);
				sEchoStr=wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
				out.print(sEchoStr);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	/*
	 * 获取企业公众号Token
	 * */
	@RequestMapping(value="/genQYToken",method = RequestMethod.GET)
	protected void GetQYToken(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String grant_type = GlobalPara.grant_type;
		String appid = GlobalPara.CorpID;
		String secret=GlobalPara.QYsecret;
		String apiUrl="https://qyapi.weixin.qq.com/cgi-bin/gettoken?"+"corpid="+appid+"&corpsecret="+secret;
		HttpGet request = new HttpGet(apiUrl);
		String result="";
		try{
			HttpResponse response = HttpClients.createDefault()
					.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();
		out.print(result); 
	}
	
	
	/*
	 * 获取企业关注者列表
	 * */
	@RequestMapping(value="/queryQYUser",method = RequestMethod.GET)
	protected void QueryQYUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String token=req.getParameter("access_token");
		String userid=req.getParameter("user");
		String apiUrl="https://qyapi.weixin.qq.com/cgi-bin/user/get?"+"access_token="+token+"&userid="+userid;
		HttpGet request = new HttpGet(apiUrl);
		String result="";
		try{
			HttpResponse response = HttpClients.createDefault()
					.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();
		out.print(result); 
	}
	
	/*
	 * 企业公众号发送消息
	 * */
	@RequestMapping(value="/QYSendMSG",method = RequestMethod.GET)
	protected void QYSendMSG(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String token=req.getParameter("access_token");
		String apiUrl="https://qyapi.weixin.qq.com/cgi-bin/message/send?"+"access_token="+token;
		
		//test1
		String result="";
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(apiUrl);
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":" + "\"" + "@all" + "\",");
		sb.append("\"toparty\":" + "\"" + "@all" + "\",");
		sb.append("\"totag\":" + "\"" + "@all" + "\",");
		sb.append("\"msgtype\":" + "\"" + "text" + "\",");
		sb.append("\"agentid\":" + "\"" + "2" + "\",");
		sb.append("\"text\":" + "{");
		sb.append("\"content\":" + "\"" + "ceshi" + "\"},");
		sb.append("\"debug\":" + "\"" + "1" + "\"");
		sb.append("}");
		post.releaseConnection();
		post.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		post.setRequestBody(sb.toString());
		try {
			client.executeMethod(post);
			result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();
		out.print(result); 

	}
	
}
