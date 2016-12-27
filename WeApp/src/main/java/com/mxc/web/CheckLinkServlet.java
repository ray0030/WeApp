package com.mxc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.crypto.Digest;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.util.WeiXinCheckUtil;
import com.weixin.util.ImageMessage;
import com.weixin.util.InputMessage;
import com.weixin.util.OutputMessage;
import com.weixin.util.SerializeXmlUtil;

@Controller
public class CheckLinkServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String token = "mxcweixin";  
    private String encodingAESKey = "OnVWt2l3HjpnVyVerEbgPKxw04pC7tupzcHVDywHmVa";  
    private String corpId = "mxc";

	public CheckLinkServlet() {

	}

	@RequestMapping(value="/linkWeiXin")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echoStr = req.getParameter("echostr"); 
		/*ArrayList<String> list = new ArrayList<String>();
		list.add(nonce);
		list.add(timestamp);
		list.add(token);
		Collections.sort(list);
		PrintWriter out = resp.getWriter();
		String result = null;  
        try {  
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey,  
                    corpId);  
            result = wxcpt.VerifyURL(signature, timestamp, nonce, echoStr);  
        } catch (AesException e) {  
            e.printStackTrace();  
        }  
        if (result == null) {  
            result = token;  
        }  
        out.print(result);  
        out.close();  
        out = null; */
		PrintWriter out = resp.getWriter();
		if(echoStr!=null){
			if(WeiXinCheckUtil.checkSignature(signature, timestamp, nonce)){
	            out.print(echoStr);
	        }else{
	        	
	        }
		}else{
	        acceptMessage(req, resp);  
	       
		}
	}


	//sha1算法
	public static String sha1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//生成随机字符串
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
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
	       // if (msgType.equals(MsgType.Text.toString())) {
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
	            str.append("<Content><![CDATA[Ray said " + inputMsg.getContent() + "]]></Content>");  
	            str.append("</xml>");  
	            System.out.println(str.toString());  
	            //response.getWriter().write(str.toString());
	            response.getWriter().write(new String(str.toString().getBytes("UTF-8"),"utf-8"));
	        }  
	        // 获取并返回多图片消息  
	        //if (msgType.equals(MsgType.Image.toString())) {
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
