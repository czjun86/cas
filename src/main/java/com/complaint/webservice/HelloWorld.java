package com.complaint.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.service.UserService;

public class HelloWorld{
	
	@Autowired
	private UserService userSerivce;
	
	@Transactional
    public void greeting(String name){
        //return "你好 "+name;
    }

    public String print() {
        return "我叫林计钦";
    }
}