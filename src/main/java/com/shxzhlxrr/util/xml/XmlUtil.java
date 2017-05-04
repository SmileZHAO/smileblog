package com.shxzhlxrr.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author zxr
 */
public class XmlUtil {

    /**
	 * 是手动的传入参数还是使用默认的判断集合的方法
	 */
    private boolean isListByName = false;

	private boolean isLower = false;

    private List<String> collectionRootNames;


    public boolean isListByName() {
        return isListByName;
    }

    public void setListByName(boolean isListByName) {
        this.isListByName = isListByName;
    }

    public List<String> getCollectionRootNames() {
        return collectionRootNames;
    }

    public void setCollectionRootNames(List<String> collectionRootNames) {
        this.collectionRootNames = collectionRootNames;
    }

	public boolean isLower() {
		return isLower;
	}

	public void setLower(boolean isLower) {
		this.isLower = isLower;
	}

//	public static void main(String[] args) {
//
////		String filePath = "d:/test/1.xml";
////		try {
////			Map<String,Object> map = parseXml(filePath);
////			System.out.println(map);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//
////		System.out.println(Dom4JUtil.toString(doc).toUpperCase());
//
//        XmlUtil xmlUtil = new XmlUtil();
//
//        xmlUtil.setListByName(true);
//
//        List<String> rootNames = new ArrayList<String>();
//
//        rootNames.add("services");
//        rootNames.add("invoices");
//        xmlUtil.setCollectionRootNames(rootNames);
//        String filePath = "d:/test/1.xml";
//        try {
//            Map<String, Object> map = xmlUtil.parseXml(filePath);
//            System.out.println(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public Map<String, Object> parseXml(String filePath) throws Exception {
        SAXReader reader = new SAXReader();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            Document doc = reader.read(new File(filePath));
            Element root = doc.getRootElement();
            parseXml(map, root, false);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return map;
    }

	public Map<String, Object> parseXmlInputStream(InputStream is) throws Exception {
		SAXReader reader = new SAXReader();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			parseXml(map, root, false);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return map;
	}

    @SuppressWarnings("unchecked")
	private Map<String, Object> parseXml(Map<String, Object> map, Element root, boolean isCollection) {
        List<Element> childs = root.elements();
        String curEleName = root.getName().toLowerCase();
		if (isList(childs)) {// 集合
            List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
            for (Element child : childs) {
                Map<String, Object> childMap = new HashMap<String, Object>();
                parseXml(childMap, child, true);
                resultMap.add(childMap);
            }
            map.put(curEleName, resultMap);
        } else {//Map
            Map<String, Object> childMap = new HashMap<String, Object>();
            for (Element child : childs) {
                if (child.elements().size() == 0) {
					if (isLower) {
						childMap.put(child.getName().toLowerCase(), child.getTextTrim());
					} else {
						childMap.put(child.getName(), child.getTextTrim());
					}
                } else {
                    parseXml(childMap, child, false);
                }
            }
			if (!isCollection) {// 当是集合的子元素的时候，不将childMap放到map中
                map.put(curEleName, childMap);
            } else {
                map.putAll(childMap);
            }
        }
        return map;
    }

    private boolean isList(List<Element> elements) {
        if (isListByName) {
            return isListByName(elements);
        }
        return isListByDef(elements);
    }

    /**
	 * 是否是集合，
	 *
	 * @param elements
	 * @return
	 */
	// TODO 这个判断是否是集合的方法有点问题，需要后期优化
    private boolean isListByDef(List<Element> elements) {
        if (elements.size() < 1) {
            return false;
        }
        String parentText = elements.get(0).getParent().getName().trim();
        String curText = elements.get(0).getName().trim();
        if (parentText.toLowerCase().equals(curText.toLowerCase() + "s")) {
            return true;
        }
        return false;
    }

    /**
	 * 手动的设置xml中集合根节点的名字，
	 *
	 * @param elements
	 * @return
	 */
    private boolean isListByName(List<Element> elements) {
        String parentText = elements.get(0).getParent().getName().trim();
        if (collectionRootNames.contains(parentText)) {
            return true;
        }
        return false;
    }

	public static Document newDocument() {
		return DocumentHelper.createDocument();
	}

	public static Element appendElementText(Element parent, String nodeName, String value) {
		Element el = parent.addElement(nodeName);
		if (value == null) {
			el.setText("");
		} else {
			el.setText(value);
		}
		return el;
	}

	public static String toString(Document doc) {
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint(); // 设置XML文档输出格式
		XMLWriter xw = new XMLWriter(writer, format);
		try {
			xw.write(doc);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return writer.toString();
	}

}
