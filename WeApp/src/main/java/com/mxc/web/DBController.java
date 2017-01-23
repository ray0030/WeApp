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

import com.db.pojo.Msg;
import com.db.pojo.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.service.MsgService;
import com.service.UserService;

@Controller
@RequestMapping("/platform")
public class DBController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MsgService msgService;
	
	/*
	 * 查询用户表
	 * */
	@RequestMapping(value="/showAll")
	@ResponseBody
	public ModelAndView  showAll(HttpServletRequest resq,HttpServletResponse resp) throws Exception{
		resq.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String sex = resq.getParameter("sex");
		String age = resq.getParameter("age");
		Map<String,String> paramMap = new HashMap<String,String>();
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
		return new ModelAndView("user_table","para",arr.toString());
	}
	
	/*
	 * 新增短信类型
	 * */
	@RequestMapping(value="/addMsg")
	@ResponseBody
	public ModelAndView  addMsg(HttpServletRequest resq,HttpServletResponse resp) throws Exception{
		resq.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String title = resq.getParameter("title");
		String desc = resq.getParameter("description");
		String picUrl = resq.getParameter("picUrl");
		String url = resq.getParameter("url");
		String type = resq.getParameter("type");
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("title", title);
		paramMap.put("desc", desc);
		paramMap.put("picUrl", picUrl);
		paramMap.put("url", url);
		paramMap.put("type", type);
		this.msgService.insertMsg(paramMap);
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("addMsgSucc","para","succ");
	}
	
	/*
	 * 新增短信类型
	 * */
	@RequestMapping(value="/delMsg")
	@ResponseBody
	public void  delMsg(HttpServletRequest resq,HttpServletResponse resp) throws Exception{
		resq.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String title = resq.getParameter("title");
		String desc = resq.getParameter("desc");
		String picUrl = resq.getParameter("picUrl");
		String url = resq.getParameter("url");
		String type = resq.getParameter("type");
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("title", title);
		paramMap.put("desc", desc);
		paramMap.put("picUrl", picUrl);
		paramMap.put("url", url);
		paramMap.put("type", type);
		this.msgService.deleteMsg(paramMap);
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonObject json = new JsonObject();
		json.addProperty("succ", "succ");
		out.print(json.toString());
	}
	
	
	
	/*
	 * 新增短信类型
	 * */
	@RequestMapping(value="/msgType")
	@ResponseBody
	public void  selectMsgType(HttpServletRequest resq,HttpServletResponse resp) throws Exception{
		resq.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		List<Msg> list = this.msgService.selectTypeList();
		JsonArray arr = new JsonArray();
		for(int i =0;i<list.size();i++){
			JsonObject json = new JsonObject();
			System.out.println(list.get(i).getType());
			json.addProperty("type", list.get(i).getType());
			arr.add(json);
		}
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(arr.toString());
	}
	
	/*
	 * 新增短信类型
	 * */
	@RequestMapping(value="/showMsg")
	@ResponseBody
	public void  showMsg(HttpServletRequest resq,HttpServletResponse resp) throws Exception{
		resq.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		List<Msg> list = this.msgService.selectMsg();
		JsonArray arr = new JsonArray();
		for(int i =0;i<list.size();i++){
			JsonObject json = new JsonObject();
			System.out.println(list.get(i).getType());
			json.addProperty("title", list.get(i).getTitle());
			json.addProperty("desc", list.get(i).getDescription());
			json.addProperty("picUrl", list.get(i).getPicUrl());
			json.addProperty("url", list.get(i).getUrl());
			json.addProperty("type", list.get(i).getType());
			arr.add(json);
		}
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(arr.toString());
	}
	
}
