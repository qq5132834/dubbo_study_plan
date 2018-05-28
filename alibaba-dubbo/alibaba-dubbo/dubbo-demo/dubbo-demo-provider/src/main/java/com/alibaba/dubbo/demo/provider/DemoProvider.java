package com.alibaba.dubbo.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.dubbo.container.Main;


/**
 * Created by ken.lj on 2017/7/31.
 */
public class DemoProvider {

    public static void main(String[] args) throws Exception {
    	
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出    	

    	/**
    	 * 换一个demo启动方式
    	 * */
//    	Main.main(null);
        
    }

}
