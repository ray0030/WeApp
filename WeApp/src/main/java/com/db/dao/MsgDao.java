package com.db.dao;

import java.util.List;
import java.util.Map;

import com.db.pojo.Msg;

public interface MsgDao {
	public void insertMsg(Map<String,String> paramMap);
	public List<Msg> selectTypeList();
	public List<Msg> selectMsg();
	public void deleteMsg(Map<String,String> paramMap);
	public List<Msg> selectMsgByUserType(Map<String,String> paramMap);
	
	public void insertMsgLog(Map<String,String> paramMap);
} 
