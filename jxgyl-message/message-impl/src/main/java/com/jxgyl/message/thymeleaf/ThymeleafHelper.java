package com.jxgyl.message.thymeleaf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.jxgyl.message.Variable;
import com.jxgyl.message.template.TemplateService;

/**
 * Thymeleaf组件
 * 
 * @author iss002
 *
 */
@Component
public class ThymeleafHelper implements TemplateService {

	@Autowired
	private SpringTemplateEngine thymeleaf;

	public String process(String template, List<Variable> vars) {
		if (template != null) {
			final Context ctx = new Context();
			if (vars != null) {
				vars.forEach(var -> ctx.setVariable(var.getName(), var.getValue()));
			}
			return thymeleaf.process(template, ctx);
		}
		return null;
	}

}
