package com.mxc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.db.pojo.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.service.UserService;

@Controller
@RequestMapping("/platform")
public class DBController {
	
	@Autowired
	private UserService userService; 
	
	/*
	 * 测试数据库连接
	 * */
	@RequestMapping(value="/showAll")
	@ResponseBody
	public ModelAndView  showAll(HttpServletRequest resq,HttpServletResponse resp) throws Exception{
		String sex = resq.getParameter("sex");
		String age = resq.getParameter("age");
		Map paramMap = new HashMap();
		paramMap.put("sex", sex);
		paramMap.put("age", age);
		List<User> users = this.userService.selectById(paramMap);
		JsonArray arr = new JsonArray();
		for(int i =0;i<users.size();i++){
			JsonObject json = new JsonObject();
			System.out.println(users.get(i).getUser());
			json.addProperty("user", users.get(i).getUser());
			json.addProperty("password", users.get(i).getPassword());
			arr.add(json);
		}
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(arr.toString()); 
		//resp.sendRedirect("user_table.jsp");
		return new ModelAndView("user_table","para",arr.toString());
	}
	
	/*
	 * 测试数据库连接
	 * */
	@RequestMapping(value="/addMsg")
	@ResponseBody
	public ModelAndView  addMsg(HttpServletRequest resq,HttpServletResponse resp) throws Exception{
		String sex = resq.getParameter("sex");
		String age = resq.getParameter("age");
		Map paramMap = new HashMap();
		paramMap.put("sex", sex);
		paramMap.put("age", age);
		List<User> users = this.userService.selectById(paramMap);
		JsonArray arr = new JsonArray();
		for(int i =0;i<users.size();i++){
			JsonObject json = new JsonObject();
			System.out.println(users.get(i).getUser());
			json.addProperty("user", users.get(i).getUser());
			json.addProperty("password", users.get(i).getPassword());
			arr.add(json);
		}
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(arr.toString()); 
		//resp.sendRedirect("user_table.jsp");
		 return new ModelAndView("user_table","para",arr.toString());
	}
}
