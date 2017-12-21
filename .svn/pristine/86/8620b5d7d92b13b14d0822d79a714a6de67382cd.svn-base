package com.smht.service.core.protocol;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

/**
 * @Description 对于接受和转发的消息进行处理的助手类
 * @author shentian
 *
 */
public class MessageOperation {

	/**
	 * @Description 把json格式的消息转成对象
	 * @param code
	 * @return
	 */
	public static Message decode(String code) {
		Message message = new Message();

		JSONObject json = JSONObject.fromObject(code, jsonConfig);
		if (json.containsKey("method")) {
			message.setMethod(json.get("method").toString());
		}

		if (json.containsKey("content")) {
			message.setContent(json.get("content").toString());
		}

		return message;
	}

	/**
	 * @Description 将message转成Json
	 * @param message
	 * @return
	 */
	public static String encode(Message message) {
		JSONObject jsonObject = new JSONObject();

		String method = message.getMethod();
		String msg = message.getMsg();
		Integer ret = message.getRet();
		Object content = message.getContent();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("method", method);
		map.put("ret", ret);
		map.put("msg", msg);
		if (content != null) {
			map.put("content", content);
		}
		jsonObject.putAll(map, jsonConfig);

		return jsonObject.toString();
	}

	private static JsonConfig jsonConfig = new JsonConfig();

	static {
		// 防止数值为null时自动赋值为0
		NullValueProcessor processor = new NullValueProcessor();
		jsonConfig.registerDefaultValueProcessor(Integer.class, processor);
		jsonConfig.registerDefaultValueProcessor(Long.class, processor);
		jsonConfig.registerDefaultValueProcessor(Double.class, processor);
		jsonConfig.registerDefaultValueProcessor(Float.class, processor);
		jsonConfig.registerDefaultValueProcessor(String.class, processor);
	}

	static class NullValueProcessor implements DefaultValueProcessor {

		@SuppressWarnings("rawtypes")
		@Override
		public Object getDefaultValue(Class clazz) {
			return null;
		}
	}
}
