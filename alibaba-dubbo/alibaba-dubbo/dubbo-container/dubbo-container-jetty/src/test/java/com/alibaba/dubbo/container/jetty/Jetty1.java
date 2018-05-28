package com.alibaba.dubbo.container.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ResourceHandler;

public class Jetty1 {
    
	public static void main(String[] args) throws Exception {
		
		Server server = new Server(8080); 
		//SelectChannelConnector connector = new SelectChannelConnector(); 
		//connector.setPort(8080);
		//server.addConnector(connector);
		ResourceHandler handler = new ResourceHandler();  //静态资源处理的handler
//		handler.setDirectoriesListed(true);  //会显示一个列表
		handler.setWelcomeFiles(new String[]{"hello.html"});
//		handler.setResourceBase("WebContent");
		handler.setResourceBase("src/main/webapp");
		server.setHandler(handler);
		server.start();
		server.join();
	
	}
}
