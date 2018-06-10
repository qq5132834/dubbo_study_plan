package com.xxx.rpc.service;

import org.springframework.stereotype.Component;

@Component("aaabbbccc")
public class DemoAutowireServiceImpl implements DemoAutowireService{

	@Override
	public String demo(String str) {
		return ""+str;
	}

}
