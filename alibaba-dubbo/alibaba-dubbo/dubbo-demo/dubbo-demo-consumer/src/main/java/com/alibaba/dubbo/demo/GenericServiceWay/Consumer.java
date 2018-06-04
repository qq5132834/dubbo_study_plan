package com.alibaba.dubbo.demo.GenericServiceWay;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.fastjson.JSON;

public class Consumer {
	
	public static void main(String[] args) {
		
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
//        context.start();
//
//        DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
//        String hello = demoService.sayHello("world "); // 执行远程方法
//
//        System.out.println("我是消费者:"+hello); // 显示调用结果
//		
		
		Object result = null;
		
		try {
			
			result = DubboUtils.invoke("demoService", "sayHello", new String[]{"java.lang.String"},  new Object[]{"world"});
			
			System.out.println("我是消费者:"+JSON.toJSONString(result));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
