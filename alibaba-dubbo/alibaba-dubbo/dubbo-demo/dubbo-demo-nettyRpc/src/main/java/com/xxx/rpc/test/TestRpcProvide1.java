package com.xxx.rpc.test;

import com.xxx.rpc.registry.zookeeper.ZooKeeperServiceRegistry;
import com.xxx.rpc.server.RpcServer;

/**
 * 测试类
 * 根据new的方式进行测试
 * */
public class TestRpcProvide1 {

	public static void main(String[] args) {
		
		ZooKeeperServiceRegistry zksr = new ZooKeeperServiceRegistry("127.0.0.1:2181");
		RpcServer rs = new RpcServer("127.0.0.1:8000", zksr);
		
		
	}
	
}
