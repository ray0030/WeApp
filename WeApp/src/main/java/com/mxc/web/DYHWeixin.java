package com.mxc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auto.util.TulingApiProcess;
import com.common.GlobalPara;
import com.google.gson.JsonObject;
import com.thoughtworks.xstream.XStream;
import com.util.WeiXinUtil;
import com.weixin.util.ImageMessage;
import com.weixin.util.InputMessage;
import com.weixin.util.OutputMessage;
import com.weixin.util.SerializeXmlUtil;


@Controller
public class DYHWeixin {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DYHWeixin() {

	}
	
	/*
	 * 链接个人公众号
	 * */
	@RequestMapping(value="/linkDingYue",method = RequestMethod.GET)
	protected void linkGRWx(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			String signature = req.getParameter("signature");
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			String echoStr = req.getParameter("echostr"); 
			PrintWriter out = resp.getWriter();
			if(WeiXinUtil.checkSignature(signature, timestamp, nonce)){  //校验认证
				out.print(echoStr); 
			}else{
	       
			}
	}
	
	@RequestMapping(value="/linkDingYue",method = RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
	        acceptMessage(req, resp);  
	}
	
	/*
	 * 获取个人公众号Token
	 * */
	@RequestMapping(value="/genDYToken",method = RequestMethod.GET)
	protected void GetToken(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String grant_type = GlobalPara.grant_type;
		String appid = GlobalPara.appid;
		String secret=GlobalPara.secret;
		String apiUrl="https://api.weixin.qq.com/cgi-bin/token?"+"grant_type="+grant_type+"&appid="+appid+"&secret="+secret;
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
	 * 新建客服接口
	 * */
	@RequestMapping(value="/createDYCustom",method = RequestMethod.GET)
	protected void CreateCustom(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String token=req.getParameter("access_token");
		String kf_account=req.getParameter("kf_account");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		String apiUrl="https://api.weixin.qq.com/customservice/kfaccount/add?"+"access_token="+token;
		HttpPost request = new HttpPost(apiUrl);
		String result="";
		JsonObject json = new JsonObject();
		json.addProperty("kf_account", kf_account);
		json.addProperty("nickname", nickname);
		json.addProperty("password", password);
		StringEntity s = new StringEntity(json.toString());
		s.setContentEncoding("UTF-8"); 
		s.setContentType("application/json");
		request.setEntity(s); 
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
	
		
	private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
	        // 处理接收消息  
	        ServletInputStream in = request.getInputStream();  
	        // 将POST流转换为XStream对象  
	        XStream xs = SerializeXmlUtil.createXstream();  //import com.thoughtworks.xstream.XStream;
	        xs.processAnnotations(InputMessage.class);  
	        xs.processAnnotations(OutputMessage.class);  
	        // 将指定节点下的xml节点数据映射为对象  
	        xs.alias("xml", InputMessage.class);  
	        // 将流转换为字符串  
	        StringBuilder xmlMsg = new StringBuilder();  
	        byte[] b = new byte[4096];  
	        for (int n; (n = in.read(b)) != -1;) {  
	            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
	        }  
	        // 将xml内容转换为InputMessage对象  
	        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());  
	  
	        String servername = inputMsg.getToUserName();// 服务端  
	        String custermname = inputMsg.getFromUserName();// 客户端  
	        long createTime = inputMsg.getCreateTime();// 接收时间  
	        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
	  
	        // 取得消息类型  
	        String msgType = inputMsg.getMsgType();  
	        // 根据消息类型获取对应的消息内容  
	        if (msgType.equalsIgnoreCase("text")) {  
	            // 文本消息  
	            System.out.println("开发者微信号：" + inputMsg.getToUserName());  
	            System.out.println("发送方帐号：" + inputMsg.getFromUserName());  
	            System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));  
	            System.out.println("消息内容：" + inputMsg.getContent());  
	            System.out.println("消息Id：" + inputMsg.getMsgId());  
	  
	            StringBuffer str = new StringBuffer();  
	            str.append("<xml>");  
	            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");  
	            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");  
	            str.append("<CreateTime>" + returnTime + "</CreateTime>");  
	            str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");  
	            //str.append("<Content><![CDATA[Ray said " + inputMsg.getContent() + "]]></Content>");
	            str.append("<Content><![CDATA[ Ray told you:" + TulingApiProcess.getTulingResult(inputMsg.getContent() ) + "]]></Content>");
	            str.append("</xml>");  
	            System.out.println(str.toString());  
	            response.getWriter().write(str.toString());
	            response.getWriter().write(new String(str.toString().getBytes("UTF-8"),"utf-8"));
	        }  
	        // 获取并返回多图片消息  
	        if (msgType.equalsIgnoreCase("image")) {  
	            System.out.println("获取多媒体信息");  
	            System.out.println("多媒体文件id：" + inputMsg.getMediaId());  
	            System.out.println("图片链接：" + inputMsg.getPicUrl());  
	            System.out.println("消息id，64位整型：" + inputMsg.getMsgId());  
	  
	            OutputMessage outputMsg = new OutputMessage();  
	            outputMsg.setFromUserName(servername);  
	            outputMsg.setToUserName(custermname);  
	            outputMsg.setCreateTime(returnTime);  
	            outputMsg.setMsgType(msgType);  
	            ImageMessage images = new ImageMessage();  
	            images.setMediaId(inputMsg.getMediaId());  
	            outputMsg.setImage(images);  
	            System.out.println("xml转换：/n" + xs.toXML(outputMsg));  
	            response.getWriter().write(xs.toXML(outputMsg));  
	        }  
	    }  
	   


	
}
