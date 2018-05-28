package com.alibaba.dubbo.object;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

//    @RequestMapping("/index")
//    public String index() {
//
//        return "hello";
//
//    }
    
    @RequestMapping("/hellojetty")
    @ResponseBody
    public String getHelloworld(){
    	System.out.println("hello jetty.");
    	return "helloworld.";
    }

}