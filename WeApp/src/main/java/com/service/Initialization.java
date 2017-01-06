package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.common.GlobalPara;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Service
public class Initialization implements ServletContextAware{
	
	ServletContext context;
	
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@PostConstruct
	public void init() {
		initContentPath();
	}
	
	
	/**
	 * 初始化项目路径
	 */
	public void initContentPath() {

		// 加载全局变量
		if (null == GlobalPara.appid)
			GlobalPara.load();

		if (null == context) {

			// 单元测试时
			System.out.println("Initialization not context.");
			return;
		}
		
		// 网站根路径
		String contextPath = context.getContextPath();
		context.setAttribute("CONTEXT_PATH", contextPath);

	}
}