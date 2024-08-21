package com.cxf.hgb;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.apache.commons.collections.MapUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

@lombok.extern.slf4j.Slf4j
public class JaxWsDynamicClientFactoryClientTest {
    public static void main(String[] args) {
        String address = "http://localhost:34091/portal/services/UserSsoService?wsdl";
//        String method = "getSsoBySeesionToken";
        String method = "getSsoBySessionToken";
        String sessionToken = "aaaa-bbbb-cccc-dddd";
        StringBuilder waitCheckMess = new StringBuilder();
        waitCheckMess.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        waitCheckMess.append("<root>");
        waitCheckMess.append("<sessionToken>").append(sessionToken).append("</sessionToken>");
        waitCheckMess.append("<sysId>" + "crmxt" + "</sysId>");
        waitCheckMess.append("</root>");

        log.info("qryPhoneNbrFromOA requestStr is {}", waitCheckMess.toString());
        Object[] results = new Object[0];
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(address);
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            long timeout = 5 * 1000;
            policy.setConnectionTimeout(timeout);
            policy.setReceiveTimeout(timeout);
            conduit.setClient(policy);
            results = client.invoke(method, waitCheckMess.toString());
            log.info("qryPhoneNbrFromOA responseStr: {}", JSON.toJSONString(results));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        Map<String, String> resultMap = xmlToMap(results[0].toString());
        // 返回手机号码
        log.info("apUserId = {}", MapUtils.getString(resultMap, "apUserId"));
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
