package com.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.MsgDao;
import com.db.pojo.Msg;

@Service("MsgService")
public class MsgService {

	@Autowired
	private MsgDao msgDao;
	
	/*public List<User> selectById(Map paramMap){
		List<User> users = this.msgDao.insertMsg(paramMap);
		return users;
	}*/
	public void insertMsg(Map<String,String> paramMap){
		this.msgDao.insertMsg(paramMap);
	}
	
	public List<Msg> selectTypeList(){
		return this.msgDao.selectTypeList();
	}
	
	public List<Msg> selectMsg(){
		return this.msgDao.selectMsg();
	}
	
	public void deleteMsg(Map<String,String> paramMap){
		this.msgDao.deleteMsg(paramMap);
	}
	
	public List<Msg> selectMsgByUserType(Map<String,String> paramMap){
		return this.msgDao.selectMsgByUserType(paramMap);
	}
	
	public void insertMsgLog(Map<String,String> paramMap){
		this.msgDao.insertMsgLog(paramMap);
	}
}
