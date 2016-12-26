package com.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class HelloImpl implements IHello {

	private String msg;
	
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String sayHi() {
        
        return "当前时间:"+new Date()+" msg:"+msg;
    }

}
