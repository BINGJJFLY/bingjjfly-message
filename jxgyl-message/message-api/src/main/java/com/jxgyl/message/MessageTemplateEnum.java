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
	PURE_TEXT(null, null) {
		@Override
		public Map<String, String> embedded() {
			return null;
		}
	},
	/**
	 * 导入用户
	 */
	IMPORT_USER("IMPORT_USER", "导入用户") {
		@Override
		public Map<String, String> embedded() {
			return null;
		}
	},
	/**
	 * 客户端用户密码初始化
	 */
	RESET_PASSWORD("RESET_PASSWORD", "首钢基金内网门户密码初始化") {
		@Override
		public Map<String, String> embedded() {
			Map<String, String> embedded = new HashMap<String, String>();
			embedded.put("logo", "static/images/logob.png");
			return embedded;
		}
	},
	/**
	 * 管理端用户重置密码
	 */
	ADMIN_RESET_PASSWORD("ADMIN_RESET_PASSWORD", "首钢基金内网门户重置密码") {
		@Override
		public Map<String, String> embedded() {
			Map<String, String> embedded = new HashMap<String, String>();
			embedded.put("logo", "static/images/logob.png");
			return embedded;
		}
	};

	private String template;
	private String subject;

	private MessageTemplateEnum(String template, String subject) {
		this.template = template;
		this.subject = subject;
	}

	public String getTemplate() {
		return template;
	}

	public String getSubject() {
		return subject;
	}

	public static MessageTemplateEnum mte(String template) throws MessageException {
		for (MessageTemplateEnum e : values()) {
			if (e.name().equals(template)) {
				return e;
			}
		}
		throw new MessageException("【没有找到符合条件的信息模板】：模板名[" + template + "]");
	}

	/**
	 * 嵌入式附件信息
	 * 
	 * @return
	 */
	public abstract Map<String, String> embedded();
}
