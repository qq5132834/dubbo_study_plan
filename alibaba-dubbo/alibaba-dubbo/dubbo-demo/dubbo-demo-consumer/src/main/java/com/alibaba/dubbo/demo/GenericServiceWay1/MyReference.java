package com.alibaba.dubbo.demo.GenericServiceWay1;

import com.alibaba.dubbo.config.annotation.Reference;

public class MyReference {
	
	@Reference(generic=true,interfaceName="com.alibaba.dubbo.demo.DemoService.sayHello")
	private static Object object ;

	public static Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
