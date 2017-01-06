package com.auto.util;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TulingApiProcess {

	private static String key = "ac54612d06864c61bd1b76ff80677481";

	public static String getTulingResult(String content) {
		String apiUrl = "http://www.tuling123.com/openapi/api?key=" + key
				+ "&info=";
		String param = "";
		try {
			param = apiUrl + URLEncoder.encode(content, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpGet request = new HttpGet(param);
		String result = "";
		try {
			HttpResponse response = HttpClients.createDefault()
					.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(null==result){  
            return "对不起，你说的话真是太高深了……";  
        }  
		try {  
            JSONObject json = JSONObject.fromObject(result);  
            //以code=100000为例，参考图灵机器人api文档  
            
            if(100000==json.getInt("code")){  
                result = json.getString("text");  
            }  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return result;  
    }  
}
