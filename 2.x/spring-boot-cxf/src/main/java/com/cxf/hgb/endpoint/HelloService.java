package com.cxf.hgb.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Author whh
 * Date 2023/12/07/ 22:22
 * <p></p>
 */
@WebService
public interface HelloService {
    @WebMethod
    String sayHello(@WebParam(name = "name")String name);
}

