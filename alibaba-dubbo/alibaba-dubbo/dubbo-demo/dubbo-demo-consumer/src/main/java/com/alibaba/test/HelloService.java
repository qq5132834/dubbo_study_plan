package com.alibaba.test;

public class HelloService {
	
	public static String sayHello(final String name)
  {
      return String.format("------------------Hello %s!", name);
  }

   public static void main(String[] args)
   {
        System.out.println(HelloService.sayHello("World"));     
   }  
}
