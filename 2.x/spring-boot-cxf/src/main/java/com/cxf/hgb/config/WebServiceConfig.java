package com.cxf.hgb.config;

import com.cxf.hgb.endpoint.HelloService;
import com.cxf.hgb.endpoint.UserSsoService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author whh
 * Date 2023/12/07/ 22:39
 * <p></p>
 */
@Configuration
public class WebServiceConfig {

    @Autowired
    private SpringBus bus;

    @Autowired
    private HelloService helloService;

    @Autowired
    private UserSsoService userSsoService;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, helloService);
        endpoint.setInInterceptors(Collections.singletonList(new LoggingInInterceptor()));
        endpoint.setOutInterceptors(Collections.singletonList(new LoggingOutInterceptor()));

        //将serviceName作为线程池前缀
        WebService annotation = helloService.getClass().getAnnotation(WebService.class);
        String prefix = annotation.serviceName();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                50,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new BasicThreadFactory.Builder().namingPattern(prefix + "-thread-pool-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        //设置线程池
        endpoint.setExecutor(executor);
        endpoint.publish("/api");
        return endpoint;
    }
    @Bean
    public Endpoint another_endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, userSsoService);
        endpoint.setInInterceptors(Collections.singletonList(new LoggingInInterceptor()));
        endpoint.setOutInterceptors(Collections.singletonList(new LoggingOutInterceptor()));

        //将serviceName作为线程池前缀
        WebService annotation = userSsoService.getClass().getAnnotation(WebService.class);
        String prefix = annotation.serviceName();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                50,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new BasicThreadFactory.Builder().namingPattern(prefix + "-thread-pool-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        //设置线程池
        endpoint.setExecutor(executor);
        endpoint.publish("/UserSsoService");
        return endpoint;
    }
}

