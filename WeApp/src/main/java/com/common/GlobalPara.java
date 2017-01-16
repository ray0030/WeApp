package com.common;

import java.io.FileInputStream;
import java.util.Properties;

public class GlobalPara {
	public static String token_url;
	public static String grant_type;
	public static String appid;
	public static String secret;
	public static String CorpID;
	public static String QYsecret;
	
	
	/*
	 * 读取配置加载参数
	 * */
	public static boolean load(){
		String strFileName = "properties/config.properties";
		Properties ps = new Properties();
		FileInputStream istream = null;
		try {
			PropUtil.getInstance();
			istream = new FileInputStream(PropUtil.getInstance().getSystemPath(
					strFileName));
			ps.load(istream);
			istream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("init failed:" + strFileName);
			Thread.interrupted();
			return false;
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (Throwable ignore) {
				}
			}
		}

		// 赋值
		token_url = ps.getProperty("token_url");
		grant_type = ps.getProperty("grant_type");
		appid = ps.getProperty("appid");
		secret = ps.getProperty("secret");
		CorpID = ps.getProperty("CorpID");
		QYsecret = ps.getProperty("QYsecret");
		
		return true;
	}
	public static void main(String[] args) {
		GlobalPara.load();
	}
	
}
