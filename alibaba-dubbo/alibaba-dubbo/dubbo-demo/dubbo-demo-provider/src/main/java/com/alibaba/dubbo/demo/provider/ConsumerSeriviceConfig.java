package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;


public class ConsumerSeriviceConfig {
	
	public static void main(String[] args) throws Exception{
		
//		ClassPathXmlApplicationContext ct= new ClassPathXmlApplicationContext("provide-dubbo-context1.xml");
//		ct.start();
		
		DemoService dem = new DemoServiceImpl();
//		setProvide();
		
		System.out.println("ok"+dem.sayHello("aaa"));
		System.out.println("----------------------------------------");
		
		for (int i=0;i<10;i++) {
			setReference();
			Thread.sleep(2000);
		}
		
		
		try {
			Thread.sleep(2000);
//			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    private static void setProvide(  ) {
    	
    	ApplicationConfig applicationConfig = new ApplicationConfig();
    	applicationConfig.setName("provide");
    	RegistryConfig registryConfig = new RegistryConfig();
    	registryConfig.setProtocol("zookeeper");
    	registryConfig.setAddress("localhost:2181"); //可以定义集群式 zookeeper注册中心
//    	registryConfig.setPort(2181);
    	ProtocolConfig protocolConfig = new ProtocolConfig();
    	protocolConfig.setName("dubbo");
    	protocolConfig.setPort(20880);
    	protocolConfig.setThreads(200);
    	
    	DemoService bean = new DemoServiceImpl();
    	
    	ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
    	
    	
    	
        service.setApplication(applicationConfig);  
        service.setRegistry(registryConfig);  
        service.setProtocol(protocolConfig);  
//        service.setMonitor(monitor);  
        service.setInterface(DemoService.class);
        service.setVersion("1.0.0");  
        service.setGroup("group");  
        service.setTimeout(6000);
        //设置接口实现类bean  
        service.setRef(bean);  
        
        // 暴露及注册服务  
        service.export();  
        
  
        
    }  
    
    
    private static void setReference(){  
    	
    	ApplicationConfig applicationConfig = new ApplicationConfig();
    	applicationConfig.setName("consumer");
    	
    	RegistryConfig registryConfig = new RegistryConfig();
    	registryConfig.setProtocol("zookeeper");
    	registryConfig.setAddress("localhost:2181");
//    	registryConfig.setPort(2181);


    	ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();   
    	reference.setApplication(applicationConfig);  
    	reference.setRegistry(registryConfig);  
//    	reference.setMonitor(monitor);  
//    	reference.setInterface(DemoService.class);
    	reference.setInterface("com.alibaba.dubbo.demo.DemoService");  
    	reference.setGroup("group");  
    	reference.setVersion("1.0.0");   
    	reference.setTimeout(30000);  
    	reference.setGeneric(true); // 声明为泛化接口  
    	reference.setCheck(false);  
    	
    	GenericService genericService = reference.get();
    	if(genericService!=null){
    		Object obj = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"xixi."});
    		System.out.println(JSON.toJSONString(obj));
    	}
 
  
    }


}