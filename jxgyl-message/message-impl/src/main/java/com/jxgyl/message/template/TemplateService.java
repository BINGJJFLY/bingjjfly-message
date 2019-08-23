package com.jxgyl.message.template;

import java.util.List;

import com.jxgyl.message.Variable;

/**
 * <b>HTML模板转字符串抽象类</b>
 * </p>
 * 支持的具体实现组件：
 * </p>
 * <ul>
 * <li>Thymeleaf组件</li>
 * <li>Freemarker组件</li>
 * <li>Velocity组件</li>
 * </ul>
 * 
 * @author iss002
 *
 */
public interface TemplateService {

	/**
	 * HTML模板转字符串
	 * 
	 * @param template
	 *            模板名
	 * @param vars
	 *            域值对
	 * @return
	 */
	String process(String template, List<Variable> vars);

}
