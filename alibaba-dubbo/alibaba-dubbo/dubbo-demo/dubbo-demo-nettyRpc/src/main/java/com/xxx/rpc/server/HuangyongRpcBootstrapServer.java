package com.xxx.rpc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xxx.rpc.registry.ServiceRegistry;
import com.xxx.rpc.registry.zookeeper.ZooKeeperServiceRegistry;

/**
 * 测试RpcServer服务是否成功配置成功
 * */
public class HuangyongRpcBootstrapServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("huangyongSpringServer.xml");
		context.start();
//		ZooKeeperServiceRegistry serviceRegistry = context.getBean("serviceRegistry",ZooKeeperServiceRegistry.class);
//		System.out.println("serviceRegistry>>"+serviceRegistry.getZkAddress());
		
		RpcServer rpcServer = context.getBean(RpcServer.class);
		System.out.println("rpcServer>>"+rpcServer.getServiceAddress());
//		System.out.println(serviceRegistry.toString());
	}
}
