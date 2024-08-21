package com.example.demo.config;

import com.example.demo.service.UserSsoService;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Configuration for exposing SOAP services with Apache CXF in Spring Boot.
 */
@Configuration
//@ImportResource("classpath:META-INF/cxf/cxf.xml") // If you have additional CXF configurations in XML
public class WebServiceConfig {

    @Autowired
    private SpringBus springBus;

    @Autowired
    private UserSsoService userSsoService;


    @Bean
    public ServletRegistrationBean<CXFServlet> cxfServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/services/*");
    }


    @Bean
    public Endpoint userSsoEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus, userSsoService);
        endpoint.publish("/UserSsoService");
        return endpoint;
    }
}
