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
        
        return "��ǰʱ��:"+new Date()+" msg:"+msg;
    }

}
