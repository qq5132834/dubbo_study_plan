package com.alibaba.test;

import java.util.concurrent.Future;

public class HelloServiceFromHystrix {
 
     public static void main(String[] args)
     {
          System.out.println(HelloServiceFromHystrix.sayHello("Worldaaaaaaaa"));     
     }  
     
     public static String sayHello(final String name)
     {
         return new SayHelloCommand(name).execute();
     }
     
     /**
      * 异步回调
      * @param name
      * @return
      */
     public static Future<String> sayHelloAsync(final String name)
     {
    	 System.out.println("queue.");
         return new SayHelloCommand(name).queue();
     }
     
     
     
}
