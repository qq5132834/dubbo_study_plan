package com.alibaba.dubbo.demo.provider;

import java.util.concurrent.ConcurrentHashMap;  
  


import org.apache.commons.lang.StringUtils;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  


import com.alibaba.dubbo.config.ApplicationConfig;  
import com.alibaba.dubbo.config.MonitorConfig;  
import com.alibaba.dubbo.config.ProtocolConfig;  
import com.alibaba.dubbo.config.ReferenceConfig;  
import com.alibaba.dubbo.config.RegistryConfig;  
import com.alibaba.dubbo.config.ServiceConfig;  
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;  
import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.dubbo.rpc.service.GenericService;   
  
public class DubboBeanContainer {  
      
    private static final Logger Log = LoggerFactory.getLogger(DubboBeanContainer.class);  
      
    private static ApplicationConfig application;  
    private static RegistryConfig registry;  
    private static ProtocolConfig protocol;  
    private static MonitorConfig monitor;  
    private static String group;  
      
  
    private static ConcurrentHashMap<String,GenericService> serviceMap = new ConcurrentHashMap<String,GenericService>();  
    private static DubboBeanContainer instance = new DubboBeanContainer();  
      
    public static DubboBeanContainer getInstance() {  
//      if (instance == null) {  
//          synchronized (DubboBeanContainer.class) {  
//              if (instance == null) {  
//                  instance = new DubboBeanContainer();                      
//              }  
//          }  
//      }  
        return instance;  
    }  
      
    /**  
     * 初始化配置  
     */  
    private static void initConfig() {  
        // 当前应用配置  
        application = new ApplicationConfig();  
        application.setName("hm-core");  
  
        String zk_address = "localhost";  
        String zk_port = "2181";  
        Log.info("*************** hm_core zookeeper服务 address： " + zk_address + ", port: " + zk_port);  
          
        registry = new RegistryConfig();  
        registry.setAddress(zk_address);  
  
        // 服务提供者协议配置  
        protocol = new ProtocolConfig();  
        protocol.setName("dubbo");  
        protocol.setThreads(200);  
        protocol.setPort(Integer.valueOf(zk_port));  
  
        monitor = new MonitorConfig();  
        monitor.setProtocol("registry");  
          
        // wangqiang406 2014-08-07   
        // 为了支持灰度和vip环境, 改为先从配置文件读  
//        group = getDubboConfigGroup();  
        Log.info("dubbo.config.group:" + group);  
    }  
      
    /**  
     * 读dubbo group配置  
     * 优先从配置文件读, 读不到再按以前的逻辑读数据库配置  
     *   
     * @return dubbo group  
     */  
    private static String getDubboConfigGroup(){  
        return "dubbo.config.group"; 
    }  
 
      
//    private DubboBeanContainer(){  
        //dubbo的治理中心，使用前访问一下  http://10.25.22.222:8180/dubbo/governance/services  
        // 用户名密码  root/root  
          
  
        //========================== openfire 调用第三方服务======================  
        //  
          
        // 当前应用配置  
//      ApplicationConfig application = new ApplicationConfig();  
//      application.setName("hm_core"); // 连接注册中心配置  
//        
//      RegistryConfig registry = new RegistryConfig();  
//      registry.setAddress("zookeeper://10.13.48.77:2181");  
////        registry.setRegister(false);  
//      registry.setPort(20880);  
//        
//      MonitorConfig monitor = new MonitorConfig();  
//      monitor.setProtocol("registry");  
//        
//        
//      ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>(); // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存  
//        
//      reference.setApplication(application);  
//      reference.setRegistry(registry);  
//      reference.setMonitor(monitor);  
//      reference.setInterface("com.paic.hm.friends.facade.CommonAccountPlatFacade"); // 弱类型接口名   
//      reference.setGroup("txt");  
//      reference.setVersion("1.0.0");   
//      reference.setGeneric(true); // 声明为泛化接口  
//      reference.setCheck(false);  
//        
//        
//      GenericService genericService = reference.get(); // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用  
//        
//      // 基本类型以及Date,List,Map等不需要转换，直接调用   
//      Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {"world"});  
//      System.out.println("invoke simple result============" + (String)result);  
//        
//        
//        
//        
//      // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map   
//      Map<String, Object> article = new HashMap<String, Object>();   
//      article.put("username", "zy");   
//      article.put("article", "who am i？");   
//        
//      Object result2 = genericService.$invoke("sayHello2",   
//                                              new String[] {"com.paic.hm.friends.facade.dto.ArticleDtoTest"},   
//                                              new Object[] {article});  
//        
//        
//      List<Map<String,Object>> rest = (List<Map<String,Object>>)result2;  
//        
//      for(Map<String,Object> map : rest){  
//          for(String key : map.keySet()){  
//              System.out.println("=====================" + map.get(key));  
//                
//          }  
//      }  
//        
          
//        try {  
//            initConfig();  
//  
//            // ===========================openfire 暴露服务给第三方调用  
//            // ================================  
//            /*  
//             * 公共配置查询接口配置  
//             */  
////            CommonPropertyService commonPropertyService = new CommonPropertyServiceImpl();  
////            dubboServiceConfig(new ServiceConfig<CommonPropertyService>(),  
////                    CommonPropertyService.class, commonPropertyService);  
//            
//            DemoService demoService = new DemoServiceImpl();
//            this.dubboServiceConfig(new ServiceConfig<DemoService>(), DemoService.class, demoService);
//  
//           
//            Log.info("succeed in dispose dubbo service");  
//        } catch (Exception e) {  
//            Log.error("初始化dubbo接口配置失败", e);  
//        }  
//  
//    }  
    
    public static void main(String[] args) throws Exception {
    	initConfig();
//    	Thread.sleep(3000);
    	DemoService demoService = new DemoServiceImpl();
    	dubboServiceConfig(new ServiceConfig<DemoService>(), DemoService.class, demoService);
		System.out.println("adf");
	}
      
    private <T> void groupDubboServiceConfig(ServiceConfig<T> service, Class<T> interfaceClass, T serviceBean) {  
        service.setApplication(application);  
        service.setRegistry(registry);  
        service.setProtocol(protocol);  
        service.setMonitor(monitor);  
        service.setInterface(interfaceClass);  
        service.setVersion("1.0.0");  
        service.setGroup(group);  
        service.setTimeout(6000);  
        //设置接口实现类bean  
        service.setRef(serviceBean);  
        // 暴露及注册服务  
        service.export();  
          
    }  
      
      
    /**  
     * 提供给外部调用的dubbo接口bean配置  
     * @param service  
     * @param clazz  
     */  
    private static <T> void dubboServiceConfig(ServiceConfig<T> service, Class<T> interfaceClass, T serviceBean) {  
        service.setApplication(application);  
        service.setRegistry(registry);  
        service.setProtocol(protocol);  
        service.setMonitor(monitor);  
        service.setInterface(interfaceClass);  
        service.setVersion("1.0.0");  
        service.setGroup(group);  
          
        //设置接口实现类bean  
        service.setRef(serviceBean);  
        // 暴露及注册服务  
        service.export();  
          
    }  
  
    private <T> void dubboLogicServiceConfig(ServiceConfig<T> service, Class<T> interfaceClass, T serviceBean,String group) {  
        service.setApplication(application);  
        service.setRegistry(registry);  
        service.setProtocol(protocol);  
        service.setMonitor(monitor);  
        service.setInterface(interfaceClass);  
        service.setVersion("1.0.0");  
        service.setGroup(group);  
          
        //设置接口实现类bean  
        service.setRef(serviceBean);  
        // 暴露及注册服务  
        service.export();  
    }  
      
      
    /**  
     * 获取要调用的dubbo接口  
     * 除调用UM以外的dubbo接口都使用这个方法  
     * 因为只有openfire和um区分多套环境  
     *   
     * @param interfaceName 接口名路径  
     * @return  
     */  
    public GenericService getServiceNotUM(String interfaceName){  
        // 从db读group, 忽略配置文件  
        String _group = getDubboConfigGroup();  
        return getService(interfaceName,_group);  
    }  
      
    /**  
     * 获取要调用的dubbo接口  
     * 只有调用UM的dubbo接口才使用这个方法  
     * 因为只有openfire和um区分多套环境  
     *   
     * @param interfaceName 接口名路径  
     * @return  
     */  
    public GenericService getService(String interfaceName){   
            return getService(interfaceName, group, null);  
    }  
      
    /**  
     * 获取要调用的dubbo接口  
     *   
     * @param interfaceName 接口名路径  
     * @param group  
     * @return  
     */  
    public GenericService getService(String interfaceName, String _group){  
        return getService(interfaceName, _group, null);  
    }  
      
    public GenericService getServiceWirhCluster(String interfaceName, String cluster){  
        return getService(interfaceName, group, cluster);  
    }  
      
//    public SessionOperation getSessionOperation(){  
//  
//        if(application == null){  
//            new DubboBeanContainer();  
//        }  
//          
//        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存  
//        ReferenceConfig<SessionOperation> reference = new ReferenceConfig<SessionOperation>();   
//          
//        reference.setApplication(application);  
//        reference.setRegistry(registry);  
//        reference.setMonitor(monitor);  
//        reference.setInterface(SessionOperation.class);    
//        reference.setGroup(ClusterConstants.DUBBO_LOGIC_GROUP);  
//        reference.setVersion("1.0.0");   
//        reference.setTimeout(500);  
//        reference.setCheck(false);  
//        reference.setScope(ClusterConstants.DUBBO_REMOTE);  
//        reference.setCluster(ClusterConstants.DUBBO_CLUSTER);   //新的路由Cluster  
//  
//        ReferenceConfigCache cache = ReferenceConfigCache.getCache();  
//        SessionOperation sessionOperation = cache.get(reference);    
//        return sessionOperation;  
//    }  
      
      
      
    /**  
     * 获取要调用的dubbo接口  
     *   
     * @param interfaceName 接口名路径  
     * @param group  
     * @return  
     */  
    private GenericService getService(String interfaceName, String _group, String cluster){  
  
        if(application == null){  
            new DubboBeanContainer();  
        }  
  
        if(serviceMap.containsKey(interfaceName)){  
            return serviceMap.get(interfaceName);  
        }else{  
            // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存  
            ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();   
              
            reference.setApplication(application);  
            reference.setRegistry(registry);  
            reference.setMonitor(monitor);  
            reference.setInterface(interfaceName); // 弱类型接口名   
            reference.setGroup(_group);  
            reference.setVersion("1.0.0");   
            reference.setTimeout(30000);  
            reference.setGeneric(true); // 声明为泛化接口  
            reference.setCheck(false);  
            if(cluster!=null && (!"".equals(cluster)))  
                reference.setCluster(cluster);  
              
            // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用  
            GenericService genericService = reference.get();  
              
            Log.info("interfaceName=" + interfaceName + ",group=" + _group);  
              
            serviceMap.putIfAbsent(interfaceName, genericService);  
              
            return genericService;  
        }  
  
    }  
      
    public GenericService getServiceWithoutGroup(String interfaceName){  
        if(application == null){  
            new DubboBeanContainer();  
        }  
  
        if(serviceMap.containsKey(interfaceName)){  
            return serviceMap.get(interfaceName);  
        }else{  
            // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存  
            ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();   
              
            reference.setApplication(application);  
            reference.setRegistry(registry);  
            reference.setMonitor(monitor);  
            reference.setInterface(interfaceName); // 弱类型接口名   
//          reference.setGroup(group);  
            reference.setVersion("1.0.0");   
            reference.setGeneric(true); // 声明为泛化接口  
            reference.setCheck(false);  
              
            // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用  
            GenericService genericService = reference.get();  
              
            Log.info("interfaceName=" + interfaceName);  
              
            serviceMap.putIfAbsent(interfaceName, genericService);  
              
            return genericService;    
        }  
    }  
  
}  