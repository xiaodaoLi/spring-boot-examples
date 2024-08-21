package com.cxf.hgb.endpoint;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * Author whh
 * Date 2023/12/07/ 22:35
 * <p></p>
 */

@WebService(serviceName = "HelloService",
        endpointInterface = "com.cxf.hgb.endpoint.HelloService")
@Component
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {

        System.out.println(Thread.currentThread().getName());
        return "Hello," + name;
    }
}

