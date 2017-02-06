package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.MsgLogDao;

@Service("MsgLogService")
public class MsgLogService {

	@Autowired
	private MsgLogDao MsgLogDao;	
	
}
