package com.alibaba.dubbo.demo.GenericServiceWay1;

import com.alibaba.fastjson.JSON;

public class Consumer2 {
	
	public static void main(String[] args) {

		
		Object result = null;
		
		try {
			
			result = DubboUtils.invoke("demoService", "sayHello", new String[]{"java.lang.String"},  new Object[]{"world"});
			
			System.out.println("我是消费者:"+JSON.toJSONString(result));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
