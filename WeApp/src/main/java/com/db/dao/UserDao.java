package com.db.dao;

import java.util.List;
import java.util.Map;

import com.db.pojo.User;


public interface UserDao {
	public User selectById1();
	public List<User> selectById(Map<String,String> paramMap);

}
