package com.cxf.hgb;

import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class XmlToMapConverter {

    public static Map<String, String> xmlToMap(String xmlString) {
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
            e.printStackTrace();
        }

        return map;
    }

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n" +
                "<root>\n" +
                "    <portaluser>lvjtest</portaluser>\n" +
                "    <apUserId>123456789</apUserId>\n" +
                "    <sessionToken>xxxxxx</sessionToken>\n" +
                "    <tokenTime>xxxxxx</tokenTime>\n" +
                "    <messagerCode>1/2/3/4</messagerCode>\n" +
                "</root>";

        Map<String, String> map = xmlToMap(xml);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println(MapUtils.getString(map, "apUserId"));
    }
}
