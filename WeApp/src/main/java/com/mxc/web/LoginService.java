package com.mxc.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.service.IHello;

/**
 * Servlet implementation class HelloServlet
 */
@Controller
public class LoginService {
	private static final long serialVersionUID = 1L;

	protected Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IHello hello;
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@RequestMapping(value="/login")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		_logger.info("=================================!!!!!!!!!!!!!!!!!!!!!!!!!");
        String sayHi = hello.sayHi();
        System.err.println("sayHi:" + sayHi);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("<h2>" + sayHi + "</h2>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
