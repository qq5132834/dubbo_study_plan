package com.alibaba.dubbo.demo.provider;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;


public class ProvideSeriviceConfig {
	
	public static void main(String[] args){
		
		
		DemoService dem = new DemoServiceImpl();
		setProvide(20880);
//		setProvide(20881);
		
		System.out.println("ok"+dem.sayHello("aaa"));
		System.out.println("----------------------------------------");
		
		setReference();
		
		try {
			Thread.sleep(2000);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    private static void setProvide( int port ) {
    	
    	ApplicationConfig applicationConfig = new ApplicationConfig();
    	applicationConfig.setName("provide");
    	RegistryConfig registryConfig = new RegistryConfig();
    	registryConfig.setProtocol("zookeeper");
    	registryConfig.setAddress("127.0.0.1:2181");
    	RegistryConfig registryConfig1 = new RegistryConfig();
    	registryConfig1.setProtocol("zookeeper");
    	registryConfig1.setAddress("127.0.0.2:2181");
    	List<RegistryConfig> registries = new ArrayList<RegistryConfig>();
    	registries.add(registryConfig);
    	registries.add(registryConfig1);
    	
//    	registryConfig.setPort(2181);
    	ProtocolConfig protocolConfig = new ProtocolConfig();
    	protocolConfig.setName("dubbo");
//    	protocolConfig.setPort(20880);
    	protocolConfig.setPort(port);
    	protocolConfig.setThreads(200);
    	
    	DemoService bean = new DemoServiceImpl();
    	
    	ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
    	
    	
    	
        service.setApplication(applicationConfig);
        //集群注册中心
        service.setRegistries(registries);
        //单个注册中心
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
    	registryConfig.setAddress("localhost");
    	registryConfig.setPort(2181);


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