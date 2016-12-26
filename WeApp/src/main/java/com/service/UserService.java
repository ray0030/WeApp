package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.UserDao;
import com.db.pojo.User;

@Service("userService")
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public List<User> selectById(){
		List<User> users = this.userDao.selectById();
		return users;
	}
}
