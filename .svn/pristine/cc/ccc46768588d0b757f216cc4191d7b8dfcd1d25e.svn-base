package com.smht.service.core.configuration;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * action配置注册表
 *
 * @author fanhaoyu
 * @since 2015年7月21日
 */
public class ActionConfigRegistry {

	private static final ActionConfigRegistry instance = new ActionConfigRegistry();

	private Map<String, ActionConfig> configMap = Maps.newHashMap();

	private ActionConfigRegistry() {
	}

	public static ActionConfigRegistry getInstance() {
		return instance;
	}

	public void register(String actionName, ActionConfig config) {
		configMap.put(actionName, config);
	}

	public ActionConfig getConfig(String actionName) {
		return configMap.get(actionName);
	}
}
