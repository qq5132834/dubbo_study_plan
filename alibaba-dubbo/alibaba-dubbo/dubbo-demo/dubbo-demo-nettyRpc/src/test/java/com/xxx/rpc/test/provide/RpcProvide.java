package com.xxx.rpc.test.provide;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcProvide {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("huangyongSpringServer.xml");
		context.start();
//		ZooKeeperServiceRegistry serviceRegistry = context.getBean("serviceRegistry",ZooKeeperServiceRegistry.class);
//		System.out.println("serviceRegistry>>"+serviceRegistry.getZkAddress());
		
//		RpcServer rpcServer = context.getBean(RpcServer.class);
//		System.out.println("rpcServer>>"+rpcServer.getServiceAddress());
//		System.out.println(serviceRegistry.toString());
	}

}
