package com.alibaba.dubbo.demo.GenericServiceWay1;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.service.GenericService;


public class DubboUtils {
private static final Logger Log = Logger.getLogger(DubboUtils.class);
	

	@SuppressWarnings("rawtypes")
	public static Object invoke(String beanName, String methodName, 
			String[] paramTypes, Object[] paramObjs) throws Exception {
		Log.info("Start to exec Dubbo method '" + beanName + "." + methodName + "'.");
		
		// 参数beanName、methodName校验
		if(StringUtils.isEmpty(beanName) || StringUtils.isEmpty(methodName)){
			Log.info("Illegal params beanName or methodName: Can not be null or empty.");
			throw new Exception("Illegal params beanName or methodName: Can not be null or empty.");
		}
		
		// 参数paramTypes、paramObjs校验
		if(paramTypes != null && paramObjs != null) {
			// 参数长度校验
			if(paramTypes.length != paramObjs.length){
				Log.info("Inconsistent length about paramTypes and paramObjs.");
				throw new Exception("Inconsistent length about paramTypes and paramObjs.");
			}
			
			// 参数paramTypes、paramObjs对应度校验
			for(int i = 0; i < paramTypes.length; i++){
				Log.info("paramTypes[" + i + "] = " + paramTypes[i] + ",\t\tparamObjs[" + i + "] = " + paramObjs[i]);
				if(paramObjs[i] != null && !Map.class.isInstance(paramObjs[i])){
					
					Class paramType = null;
					try {
						paramType = Class.forName(paramTypes[i]);
					} catch (Exception e) {
						Log.info("ClassNotFoundException: " + paramTypes[i]);
						throw new Exception("ClassNotFoundException: " + paramTypes[i]);
					}
					
					if(!paramType.isInstance(paramObjs[i])){
						Log.info("Unmatched type in paramTypes and paramObjs: " 
								+ paramObjs[i] + " is not a instance of class " + paramType.getName());
						throw new Exception("Unmatched type in paramTypes and paramObjs: " 
								+ paramObjs[i] + " is not a instance of class " + paramType.getName());
					}
				}
			}
		}
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
		context.start();
		GenericService service = (GenericService) context.getBean(beanName);
		
		if(service == null){
			Log.info("Can not get Bean '" + beanName + "': null.");
			throw new Exception("Can not get Bean '" + beanName + "': null.");
		}
		
		try {
			Object result = null;
    		// 开始实际调用第三方Dubbo接口
			long startTime = System.currentTimeMillis();
    		result = service.$invoke(methodName, paramTypes, paramObjs);
    		Log.info("wasted timeMillis in executing Dubbo method '" + methodName + "': "
    				+ (System.currentTimeMillis() - startTime));
			Log.info("End to exec Dubbo method '" + methodName + "': " + result);
			return result;
			
		} catch (Exception e) {
			Log.info("Failed to invoke '" + methodName + "': " + e.getMessage());
			throw new Exception("Failed to invoke '" + methodName + "': " + e.getMessage());
		}
		
	}
	
}