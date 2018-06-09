package com.xxx.rpc.service;

import com.xxx.rpc.server.RpcService;


@RpcService(HelloService.class) // 指定远程接口
public class HelloServiceImpl implements HelloService{

	@Override
    public String hello(String name) {
        return "Hello! " + name;
    }

}
