package com.smht.service.core.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Action配置解析器
 *
 * @author fanhaoyu
 * @since 2015年7月21日
 */
public class ActionConfigParseHandler extends DefaultHandler {
	// 存储正在解析的元素的数据
	private Map<String, String> map = null;
	// 存储所有解析的元素的数据
	private List<ActionConfig> list = null;
	// 正在解析的元素的名字
	String currentTag = null;
	// 正在解析的元素的元素值
	String currentValue = null;
	// 开始解析的元素
	String nodeName = "config";
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionConfigParseHandler.class);

	public ActionConfigParseHandler() {
	}

	public List<ActionConfig> getList() {

		return list;
	}

	// 开始解析文档，即开始解析XML根元素时调用该方法
	@Override
	public void startDocument() throws SAXException {
		// 初始化Map
		list = new ArrayList<ActionConfig>();
	}

	// 开始解析每个元素时都会调用该方法
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// 判断正在解析的元素是不是开始解析的元素
		if (qName.equals(nodeName)) {
			map = new HashMap<String, String>();
		}

		// 判断正在解析的元素是否有属性值,如果有则将其全部取出并保存到map对象中，如:<person id="00001"></person>
		if (attributes != null && map != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				map.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
		currentTag = qName; // 正在解析的元素
	}

	// 解析到每个元素的内容时会调用此方法
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (currentTag != null && map != null) {
			currentValue = new String(ch, start, length);
			// 如果内容不为空和空格，也不是换行符则将该元素名和值和存入map中
			if (currentValue != null && !currentValue.trim().equals("") && !currentValue.trim().equals("\n")) {
				map.put(currentTag, currentValue);
			}
			// 当前的元素已解析过，将其置空用于下一个元素的解析
			currentTag = null;
			currentValue = null;
		}
	}

	// 每个元素结束的时候都会调用该方法
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// 判断是否为一个节点结束的元素标签
		if (qName.equals(nodeName)) {
			ActionConfig actionConfig = new ActionConfig();
			for (String key : map.keySet()) {
				String fieldValue = map.get(key);
				if (key.equals("actionName")) {
					actionConfig.setActionName(fieldValue);
				} else if (key.equals("action")) {
					actionConfig.setAction(fieldValue);
				} else if (key.equals("method")) {
					actionConfig.setMethod(fieldValue);
				} else if(key.equals("isRetStream")){
					try {
						actionConfig.setRetStream(Boolean.parseBoolean(fieldValue));
					} catch (Exception e) {
						LOGGER.error("setRetStream fail..",e);
					}
				}
			}
			list.add(actionConfig);
			map = null;
		}
	}

	// 结束解析文档，即解析根元素结束标签时调用该方法
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
}
