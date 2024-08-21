package com.example.demo.service.impl;

import com.example.demo.service.UserSsoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service implementation Bean（SIB）
 * RPC接口实现
 * 注意这里的targetNamespace的命名空间为SEI对应的命名空间，注意最后要加/
 * 否则利用CXF动态客户端调用时，会找不到
 * Exception in thread "main" org.apache.cxf.common.i18n.UncheckedException:
 * No operation was found with the name {http://impl.service.demo.example.com/}sum.
 *
 * @author donald
 * 2017年7月7日
 * 下午5:11:49
 */
@Component
@WebService(endpointInterface = "com.example.demo.service.UserSsoService",
        serviceName = "UserSsoWSService",
        portName = "ssoMssServicePort",
        targetNamespace = "http://www.portal.cn/v3.0/"
//        targetNamespace = "http://www.donald.service/jws_service/"
)
@Slf4j
public class UserSsoServiceImpl implements UserSsoService {

    @Override
    public String getSsoBySeesionToken(String xmlString) {
        log.info("getSsoBySeesionToken param is {}", xmlString);
        Map<String, String> inputMap = xmlToMap(xmlString);
        String phoneNumber = "18900001145";
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
        xmlBuilder.append("<root>\n");
        xmlBuilder.append("    <portaluser>").append("lvjtest").append("</portaluser>\n");
        xmlBuilder.append("    <apUserId>").append(phoneNumber).append("</apUserId>\n");
        xmlBuilder.append("    <sessionToken>").append(inputMap.get("sessionToken")).append("</sessionToken>\n");
        xmlBuilder.append("    <tokenTime>").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")).append("</tokenTime>\n");
        xmlBuilder.append("    <messagerCode>").append("1/2/3/4").append("</messagerCode>\n");
        xmlBuilder.append("</root>");

        return xmlBuilder.toString();

    }

    /**
     * 解析OA响应信息，将响应转为map
     *
     * @param xmlString
     * @return
     */
    private static Map<String, String> xmlToMap(String xmlString) {
      /*
      响应信息格式
      <?xml version="1.0" encoding="GB2312"?>
      <root>
                  <portaluser>lvjtest</portaluser>
                  <apUserId>xxxxxx</apUserId >
                  <sessionToken>xxxxxx</sessionToken >
                  <tokenTime>xxxxxx</tokenTime >
                  <messagerCode>1/2/3/4</messagerCode>
      </root>
       */

        Map<String, String> map = new HashMap<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));

            Element root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    map.put(element.getTagName(), element.getTextContent());
                }
            }
        } catch (Exception e) {
            log.error(null, e, e.getMessage());
        }
        return map;
    }
}
