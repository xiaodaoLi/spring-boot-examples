package com.cxf.hgb.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.spi.ObjectFactory;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = "http://www.portal.cn/v3.0/", name = "userSsoWSService")
@XmlSeeAlso({ObjectFactory.class})
public interface UserSsoService {

    @WebMethod
    @RequestWrapper(localName = "getSsoBySeesionToken", targetNamespace = "http://www.portal.cn/v3.0/", className = "com.example.yourservice.GetSsoBySeesionToken")
    @ResponseWrapper(localName = "getSsoBySeesionTokenResponse", targetNamespace = "http://www.portal.cn/v3.0/", className = "com.example.yourservice.GetSsoBySeesionTokenResponse")
    @WebResult(name = "return", targetNamespace = "")
    public String getSsoBySeesionToken(
            @WebParam(name = "xmlString", targetNamespace = "")
            String xmlString);
}