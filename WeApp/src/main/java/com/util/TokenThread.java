package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.bean.AccessToken;

public class TokenThread implements Runnable{
	
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);    
    
    public static AccessToken accessToken = null;    
	    
	public void run() {
		while(true){
			try{
				accessToken = WeiXinUtil.getAccessToken();
				if (null != accessToken) {
					
					Thread.sleep((accessToken.getExpiresIn()-200)*1000);
				}else{
					Thread.sleep(60*1000);
				}
			}catch(InterruptedException e){
				try{
					Thread.sleep(60*1000);
				}catch(InterruptedException  e1){
					log.error("thread:"+Thread.currentThread().getId()+" excuted error...");
				}
			}
		}
	}
	
	
}
