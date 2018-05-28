/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.container.jetty;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.container.Container;
import com.alibaba.dubbo.container.page.PageServlet;
import com.alibaba.dubbo.container.page.ResourceFilter;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.FilterHolder;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * JettyContainer. (SPI, Singleton, ThreadSafe)
 *
 * @author william.liangf
 */
public class JettyContainer implements Container {

    public static final String JETTY_PORT = "dubbo.jetty.port";
    public static final String JETTY_DIRECTORY = "dubbo.jetty.directory";
    public static final String JETTY_PAGES = "dubbo.jetty.page";
    public static final int DEFAULT_JETTY_PORT = 8080;
    private static final Logger logger = LoggerFactory.getLogger(JettyContainer.class);
    SelectChannelConnector connector;

    public void start() {
    	
    	System.out.println("启动 JettyContainer ");
    	
        String serverPort = ConfigUtils.getProperty(JETTY_PORT);
        int port;
        if (serverPort == null || serverPort.length() == 0) {
            port = DEFAULT_JETTY_PORT;
        } else {
            port = Integer.parseInt(serverPort);
        }
        connector = new SelectChannelConnector();
        connector.setPort(port);
        ServletHandler handler = new ServletHandler();

        String resources = ConfigUtils.getProperty(JETTY_DIRECTORY);
        System.out.println("-------------");
        System.out.println(resources);
        System.out.println("+++++++++++++");
        if (resources != null && resources.length() > 0) {
            FilterHolder resourceHolder = handler.addFilterWithMapping(ResourceFilter.class, "/*", Handler.DEFAULT);
            resourceHolder.setInitParameter("resources", resources);
        }

        ServletHolder pageHolder = handler.addServletWithMapping(PageServlet.class, "/*");
        System.out.println(">>"+ConfigUtils.getProperty(JETTY_PAGES));
        pageHolder.setInitParameter("pages", ConfigUtils.getProperty(JETTY_PAGES));
        pageHolder.setInitOrder(2);

        Server server = new Server();
        
        /**
         * 上下文配置信息
         * */
        String DEFAULT_APP_CONTEXT_PATH = "src/main/resources";
        WebAppContext webAppCtx = new WebAppContext(DEFAULT_APP_CONTEXT_PATH, "/dubbo-container-jetty");
        webAppCtx.setDescriptor(DEFAULT_APP_CONTEXT_PATH + "/META-INF/WEB-INF/web.xml");
        webAppCtx.setResourceBase(DEFAULT_APP_CONTEXT_PATH);
        webAppCtx.setClassLoader(Thread.currentThread().getContextClassLoader());
        
        /**
         * 静态资源配置信息
         * */
        ResourceHandler staticResource = new ResourceHandler();  //静态资源处理的handler
        staticResource.setWelcomeFiles(new String[]{"index.html"});
        staticResource.setResourceBase("src/main/webapp/");
        
        server.setHandlers(new Handler[]{webAppCtx,staticResource});
        
        server.addConnector(connector);
        server.addHandler(handler);
        
        
        try {
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start jetty server on " + NetUtils.getLocalHost() + ":" + port + ", cause: " + e.getMessage(), e);
        }
    }

    public void stop() {
        try {
            if (connector != null) {
                connector.close();
                connector = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

}