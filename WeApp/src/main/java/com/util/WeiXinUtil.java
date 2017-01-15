package com.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.common.GlobalPara;
import com.util.bean.AccessToken;
import com.weixin.util.HttpUtil;

public class WeiXinUtil {

    private static final String token = "mxcweixin";
    
    public static AccessToken getAccessToken(){
    	AccessToken accessToken = null;
    	String grant_type = GlobalPara.grant_type;
		String appid = GlobalPara.appid;
		String secret=GlobalPara.secret;
		String apiUrl="https://api.weixin.qq.com/cgi-bin/token?"+"grant_type="+grant_type+"&appid="+appid+"&secret="+secret;
		JSONObject jsonObj=HttpUtil.httpRequest(apiUrl, "get", null);
		if(jsonObj != null){
			try{
				accessToken = new AccessToken();
				accessToken.setToken(jsonObj.getString("access_token"));
				accessToken.setExpiresIn(jsonObj.getInt("expires_in"));
			}catch(JSONException e){
				accessToken = null;
			}
		}
		return accessToken;
    	
    }

    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr = new String[]{token,timestamp,nonce};
        Arrays.sort(arr);

        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        String temp = getSha1(content.toString());

        return temp.equals(signature);

    }

    public static String getSha1(String str){
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}