package com.cxf.hgb;

import com.cxf.hgb.endpoint.HelloService;
import com.cxf.hgb.endpoint.UserSsoService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Author whh
 * Date 2023/12/07/ 22:58
 * <p></p>
 */
public class JaxWsProxyFactoryBeanClientTest {


    public static void main(String[] args) {

        sayHello();

        getSsoBySeesionToken();

    }

    private static void getSsoBySeesionToken() {
        String address = "http://localhost:34091/portal/services/UserSsoService?wsdl";
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(UserSsoService.class);
        // 创建一个代理接口实现
        UserSsoService xmlEndPoint = (UserSsoService) jaxWsProxyFactoryBean.create();

        Client proxy = ClientProxy.getClient(xmlEndPoint);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(5000);
        policy.setReceiveTimeout(5000);
        conduit.setClient(policy);

        String service = xmlEndPoint.getSsoBySeesionToken("aaaa-bbbb-cccc-dddd");

        System.out.println(service);
    }

    private static void sayHello() {
        String address = "http://localhost:34091/portal/services/api?wsdl";
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(HelloService.class);
        // 创建一个代理接口实现
        HelloService xmlEndPoint = (HelloService) jaxWsProxyFactoryBean.create();

        Client proxy = ClientProxy.getClient(xmlEndPoint);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(5000);
        policy.setReceiveTimeout(5000);
        conduit.setClient(policy);

        String service = xmlEndPoint.sayHello("123");

        System.out.println(service);
    }
}

