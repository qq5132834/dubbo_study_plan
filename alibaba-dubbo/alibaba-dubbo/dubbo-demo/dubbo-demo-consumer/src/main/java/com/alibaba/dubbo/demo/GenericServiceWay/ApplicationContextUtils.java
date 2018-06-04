package com.alibaba.dubbo.demo.GenericServiceWay;


import org.springframework.context.ApplicationContext;

public class ApplicationContextUtils {

	private  static ApplicationContext ac;

	public static ApplicationContext getApplicationContext() {
		return ac;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ac = applicationContext;
	}
	
	/**
	 * 根据bean名称获取bean对象
	 * @param beanName bean名称
	 * @return bean对象
	 */
	public static Object getBean(String beanName){
		return ac.getBean(beanName);
	}
}