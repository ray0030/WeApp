package com.mxc.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.db.pojo.User;
import com.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	/*
	 * 测试数据库连接
	 * */
	@RequestMapping(value="/showAll")
	public void showAll(HttpServletRequest resq,HttpServletResponse resp){
		List<User> users = this.userService.selectById();
		for(int i =0;i<users.size();i++){
			System.out.println(users.get(i).getId());
		}
	}
}
