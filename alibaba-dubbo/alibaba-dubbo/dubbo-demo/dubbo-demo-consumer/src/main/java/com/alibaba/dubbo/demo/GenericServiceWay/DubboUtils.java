package com.alibaba.dubbo.demo.GenericServiceWay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.service.GenericService;

/**
 * 调用第三方系统的Dubbo方法工具类
 * 
 * @author EX-ZHANGHAIGANG001
 *
 */
public class DubboUtils {
private static final Logger Log = Logger.getLogger(DubboUtils.class);
	
	/**
	 * 调用第三方系统的Dubbo方法
	 * 
	 * @param beanName 方法所在的bean的ID
	 * @param methodName 待调用的第三方已暴露的dubbo方法名
	 * @param paramTypes 调用方法的参数类型
	 * @param paramObjs 调用方法的参数值
	 * @return 返回结果为Object类型（对于基本数据类型(如Boolean、String、Integer、Long等)，可直接强转；
	 * 		对于非基本的数据类型(不含集合类型如List)，其返回结果可强转为Map<String,Object>；
	 * 		对于集合类型且集合类型的元素不为基本数据类型（如List<DTO>），则可强转为如List<Map<String,Object>>形式）
	 * @throws Exception
	 */
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
		
//		GenericService service = (GenericService) ApplicationContextUtils.getApplicationContext().getBean(beanName);
		
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
	
	/**
	 * Map转为Bean
	 * 
	 * @param map 转换源
	 * @param obj 转换目标结果
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private static void transMap2Bean2(Map<String, Object> map, Object obj) throws Exception {
		Log.info("Start transMap2Bean2: " + map);
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }

        } catch (Exception e) {
        	Log.info("transMap2Bean2 Error " + e);
        	throw e;
        }

        return;
    }
	
    /**
     * Map转为Bean
     * 
     * @param map 转换源
     * @param obj 转换目标结果
     * @throws Exception 
     */
    public static void transMap2Bean(Map<String, Object> map, Object obj) throws Exception {
//    	Log.info("Start transMap2Bean: " + map);
//        if (map == null || obj == null) {
//            return;
//        }
//        try {
//            BeanUtils.populate(obj, map);
//        } catch (Exception e) {
//        	Log.info("transMap2Bean Error " + e);
//        	throw e;
//        }
    }

    /**
     * Bean转为Map
     * 
     * @param obj 转换源
     * @return 转换结果
     * @throws Exception 
     */
    public static Map<String, Object> transBean2Map(Object obj) throws Exception {
    	Log.info("Start transBean2Map: " + obj);
        if(obj == null){
            return null;
        }        
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
        	Log.info("transBean2Map Error " + e);
        	throw e;
        }

        return map;

    }
	
}