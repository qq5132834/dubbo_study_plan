package com.huangyong.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试RpcServer服务是否成功配置成功
 * */
public class HuangyongRpcBootstrapServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("huangyongSpringServer.xml");
		context.start();
		ServiceRegistry serviceRegistry = context.getBean("serviceRegistry",ServiceRegistry.class);
		System.out.println(serviceRegistry.toString());
	}
}
