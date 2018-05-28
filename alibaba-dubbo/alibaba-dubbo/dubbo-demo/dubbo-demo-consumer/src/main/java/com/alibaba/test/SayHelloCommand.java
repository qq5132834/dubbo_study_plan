package com.alibaba.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class SayHelloCommand  extends HystrixCommand<String> {
    private final String _name;
    public SayHelloCommand(String name)
    {
//        super(HystrixCommandGroupKey.Factory.asKey("HelloService"));
    	
    	super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloServiceGroup--hl")) //线程
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                								.withExecutionTimeoutInMilliseconds(500)
                								.withExecutionIsolationThreadTimeoutInMilliseconds(500)
                		)
    		);
    	
    	System.out.println("construct:"+name);
    	
        _name = new String(name);
        
    }
    
    /**
     * 所有从run()方法抛出的异常都算作失败，并触发降级getFallback()和断路器逻辑。
     * */
    @Override
    protected String run() {
    	try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	String str = Thread.currentThread().getName() + ",Hello " + this._name;
        return str;
    }
    
    
    @Override
    protected String getFallback() {
    	
    	
        return String.format("[FallBack]Hello %s!", _name);
    }
    
    
}