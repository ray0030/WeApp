package com.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.GlobalPara;
import com.util.TokenThread;

public class InitServlet extends HttpServlet {

	 private static final long serialVersionUID = 1L;    
	 private static Logger log = LoggerFactory.getLogger(InitServlet.class);    
	 
	 public void init() throws ServletException{
		String grant_type = GlobalPara.grant_type;
		String appid = GlobalPara.appid;
		String secret=GlobalPara.secret;
		if("".equalsIgnoreCase(grant_type) || "".equalsIgnoreCase(appid) || "".equalsIgnoreCase(secret)){
			log.error("init token servlet :"+Thread.currentThread().getId()+" execute error...");
		}else{
			new Thread(new TokenThread()).start();
		}
	 }
	 
	 
}
