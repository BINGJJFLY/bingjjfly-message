package com.jxgyl.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息对应业务
 * <p>
 * 业务类型对应模板
 * </p>
 * 
 * @author iss002
 *
 */
public enum MessageTemplateEnum {

	/**
	 * 纯文本，不使用任何模板
	 */
	PURE_TEXT(null) {
		@Override
		public Map<String, String> embedded() {
			return null;
		}
	},
	/**
	 * 导入用户
	 */
	IMPORT_USER("IMPORT_USER") {
		@Override
		public Map<String, String> embedded() {
			return null;
		}
	},
	RESET_PASSWORD("RESET_PASSWORD") {
		@Override
		public Map<String, String> embedded() {
			Map<String, String> embedded = new HashMap<String, String>();
			embedded.put("logo", "static/images/logo.png");
			return embedded;
		}
	};

	private String template;

	private MessageTemplateEnum(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}

	/**
	 * 嵌入式附件信息
	 * 
	 * @return
	 */
	public abstract Map<String, String> embedded();
}
