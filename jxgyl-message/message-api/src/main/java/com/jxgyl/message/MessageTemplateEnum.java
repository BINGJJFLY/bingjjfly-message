package com.jxgyl.message;

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
	PURE_TEXT(null),
	/**
	 * 导入用户
	 */
	IMPORT_USER("IMPORT_USER");

	private String template;

	private MessageTemplateEnum(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}

}
