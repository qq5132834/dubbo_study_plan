package com.xxx.rpc.service;

import com.xxx.rpc.server.RpcService;


@RpcService(value=HelloService.class,name="huagliao") // 指定远程接口
public class HelloServiceImpl implements HelloService{

	@Override
    public String hello(String name) {
        return "Hello! " + name;
    }

}
