package com.alibaba.dubbo.demo.GenericServiceWay1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;

public class Consumer2 {
	

	
	public static void main(String[] args) {
	 
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer1.xml"});
		context.start();
		Object obj = context.getBean("demoService");
		System.out.println(">>"+obj.getClass().getName());

	 
		GenericService service = (GenericService) obj;
		Object result = service.$invoke("sayHello", new String[]{"java.lang.String"},  new Object[]{"world------"});

		System.out.println(JSON.toJSONString(result)); 
		
//		
//		GenericService service1 = (GenericService)MyReference.getObject();
//		Object result1 = service1.$invoke("sayHello", new String[]{"java.lang.String"},  new Object[]{"world"});
//		
		System.out.println("over");
		
	}

}
