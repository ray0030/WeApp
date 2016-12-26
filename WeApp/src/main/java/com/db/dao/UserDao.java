package com.db.dao;

import java.util.List;

import com.db.pojo.User;


public interface UserDao {
	public User selectById1();
	public List<User> selectById();

}
