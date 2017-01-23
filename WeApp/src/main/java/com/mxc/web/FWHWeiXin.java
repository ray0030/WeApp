package com.mxc.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auto.util.TulingApiProcess;
import com.common.GlobalPara;
import com.google.gson.JsonObject;
import com.service.MsgService;
import com.thoughtworks.xstream.XStream;
import com.util.TokenThread;
import com.util.WeiXinUtil;
import com.util.bean.AccessToken;
import com.weixin.util.HttpUtil;
import com.weixin.util.ImageMessage;
import com.weixin.util.InputMessage;
import com.weixin.util.OutputMessage;
import com.weixin.util.SerializeXmlUtil;
import com.db.pojo.Msg;


@Controller
public class FWHWeiXin {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private static Logger  log = LoggerFactory.getLogger(FWHWeiXin.class);
	
	
	@Autowired
	private MsgService msgService;
	
	public FWHWeiXin() {

	}

	/*
	 *longmel@163.com 链接个人公众号
	 * */
	@RequestMapping(value="/linkFuWu",method = RequestMethod.GET)
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
	
	/*
	 * 获取个人公众号Token
	 * */
	@RequestMapping(value="/genFWToken",method = RequestMethod.GET)
	protected void GetToken(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String grant_type = GlobalPara.grant_type;
		String appid = GlobalPara.appid;
		String secret=GlobalPara.secret;
		String apiUrl="https://api.weixin.qq.com/cgi-bin/token?"+"grant_type="+grant_type+"&appid="+appid+"&secret="+secret;
		JSONObject jsonObj=HttpUtil.httpRequest(apiUrl, "GET", null);
		/*HttpGet request = new HttpGet(apiUrl);
		String result="";
		try{
			HttpResponse response = HttpClients.createDefault()
					.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		PrintWriter out = resp.getWriter();
		out.print(jsonObj.toString()); 
	}

	
	@RequestMapping(value="/linkFuWu",method = RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
	        acceptMessage(req, resp);  
	}
	
	/*
	 * 新建客服接口
	 * */
	@RequestMapping(value="/createFWCustom",method = RequestMethod.GET)
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
	        //test测试图文消息
	        if (msgType.equalsIgnoreCase("text")) {  
	            // 文本消息  
	            System.out.println("开发者微信号：" + inputMsg.getToUserName());  
	            System.out.println("发送方帐号：" + inputMsg.getFromUserName());  
	            System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));  
	            System.out.println("消息内容：" + inputMsg.getContent());  
	            System.out.println("消息Id：" + inputMsg.getMsgId());  
	            
	            //test根据用户类型查询短信类型
	            Map<String,String> paramM = new HashMap<String,String>();
	            paramM.put("userid", custermname);
	            List<Msg> msgL = this.msgService.selectMsgByUserType(paramM);
	            //end
	            String id = msgL.get(0).getId();
	            String title = msgL.get(0).getTitle();
	            String desc = msgL.get(0).getDescription();
	            String picUrl = msgL.get(0).getPicUrl();
	            String url = msgL.get(0).getUrl();
	            StringBuffer str = new StringBuffer();  
	            str.append("<xml>");  
	            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");  
	            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");  
	            str.append("<CreateTime>" + returnTime + "</CreateTime>");  
	            str.append("<MsgType><![CDATA[news]]></MsgType>");  
	            str.append("<ArticleCount>2</ArticleCount>");  
	            str.append("<Articles>"); 
	            str.append("<item>"); 
	            str.append("<Title><![CDATA["+title+"]]></Title>");
	            str.append("<Description><![CDATA["+desc+"]]></Description>");
	            str.append("<PicUrl><![CDATA["+picUrl+"]]></PicUrl>");
	            str.append("<Url><![CDATA["+url+"]]></Url>");
	            str.append("</item>"); 
	            str.append("<item>"); 
	            str.append("<Title><![CDATA["+title+"2]]></Title>");
	            str.append("<Description><![CDATA["+desc+"2]]></Description>");
	            str.append("<PicUrl><![CDATA["+picUrl+"]]></PicUrl>");
	            str.append("<Url><![CDATA["+url+"]]></Url>");
	            str.append("</item>");
	            str.append("</Articles>");
	            str.append("</xml>");  
	            System.out.println(str.toString());  
	            response.getWriter().write(str.toString());
	            response.getWriter().write(new String(str.toString().getBytes("UTF-8"),"utf-8"));
	            
	            //insert log (send msg) 
	            Map<String,String> paramM1 = new HashMap<String,String>();
	            paramM1.put("id", id);
	            paramM1.put("userid", custermname);
	            paramM1.put("wxid", servername);
	            this.msgService.insertMsgLog(paramM1);
	            
	            //end
	        }
	        //test end
	       /* // 根据消息类型获取对应的消息内容  
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
	        }  */
	    }  
	   

	/*
	 * 创建图文消息模板
	 * */
	@RequestMapping(value="/uploadnews",method = RequestMethod.GET)
	protected void uploadNews(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String token = "";	
		String apiUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="+TokenThread.accessToken.getToken() ;
		String data = "{\"articles\": [{\"thumb_media_id\":\"BW4eDIdYSvO7AFjfsZKsQ9ujNma_TkCj3VSo3JNTQkYmk_iPuhpUKm48oZ4umHED\",\"author\":\"xxx\",\"title\":\"Happy Day\",\"content_source_url\":\"www.qq.com\",\"content\":\"content\",\"digest\":\"digest\",\"show_cover_pic\":\"0\"}]}";  
        String data1 = "{\"articles\":[{\"author\":\"王传清|毕强|Wang Chuanqing|Bi Qiang\",\"content\":\"基于关联关系维度的数字资源聚合是数字资源知识发现的重要基础和工具。超网络是由多个类型的同质和异质子网络组成的网络，通过多种关联维度聚合的数字资源即形成了拥有相同以及不同性质的结点和关系的数字资源超网络，这些不同性质的关联与链接是知识关联、挖掘、发现与创新的脉络线索。结合超网络理论，构建和描述数字资源超网络，并分析超网络中不同性质的关系类型，如引用关系、共现关系、耦合关系等，从关联维度探讨数字资源深度聚合的模式，进而分析利用数字资源超网络进行知识发现的具体应用方法，最后构建数字资源超网络聚合系统模型。\",\"content_source_url\":\"http://d.g.wanfangdata.com.cn/Periodical_qbxb201501002.aspx\",\"digest\":\"测试\",\"show_cover_pic\":1,\"thumb_media_id\":\"BW4eDIdYSvO7AFjfsZKsQ9ujNma_TkCj3VSo3JNTQkYmk_iPuhpUKm48oZ4umHED\",\"title\":\"超网络视域下的数字资源深度聚合研究\"}]}";  
        
        JSONObject jsonObj = HttpUtil.httpRequest(apiUrl, "POST", data1);
        PrintWriter out = resp.getWriter();
		out.print(jsonObj.toString()); 
	}
	
	@RequestMapping(value="/sendMSG",method=RequestMethod.GET)
	protected void  sendMSG(HttpServletRequest req,HttpServletResponse resp)
			throws ServletException, IOException{
		 	String urlstr ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; //发送客服图文消息
	        urlstr =urlstr.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
	        String reqjson =createGroupText();
	        try {
	            URL httpclient =new URL(urlstr);
	            HttpURLConnection conn =(HttpURLConnection) httpclient.openConnection();
	            conn.setConnectTimeout(5000);
	            conn.setReadTimeout(2000);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	            conn.setDoOutput(true);        
	            conn.setDoInput(true);
	            conn.connect();
	            OutputStream os= conn.getOutputStream();    
	            System.out.println("ccccc:"+reqjson);
	            os.write(reqjson.getBytes("UTF-8"));//传入参数    
	            os.flush();
	            os.close();
	             
	            InputStream is =conn.getInputStream();
	            int size =is.available();
	            byte[] jsonBytes =new byte[size];
	            is.read(jsonBytes);
	            String message=new String(jsonBytes,"UTF-8");
	            System.out.println("test send Text By Openids:"+message);
	          
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	}
	    
    //创建发送的数据
    private String createGroupText(){
    	 JSONArray array = new JSONArray();
         JSONObject gjson =new JSONObject();
         //JSONObject json = getUserOpenids(array.get(3).toString()); //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
         gjson.put("touser", "o7tZLuBYucdwt3j45_p41xIsMTQ0");
         gjson.put("msgtype", "news");
         JSONObject news =new JSONObject();
         JSONArray articles =new JSONArray();
         JSONObject list =new JSONObject();
         list.put("title","标准的测试标题"); //标题
         list.put("description","测试一把"); //描述
         list.put("url","http://www.baidu.com"); //点击图文链接跳转的地址
         list.put("picurl","http://"); //图文链接的图片
         articles.add(list);
         news.put("articles", articles);
         JSONObject text =new JSONObject();
         gjson.put("text", text);
         gjson.put("news", news);
          
        return gjson.toString();
    }
}		
