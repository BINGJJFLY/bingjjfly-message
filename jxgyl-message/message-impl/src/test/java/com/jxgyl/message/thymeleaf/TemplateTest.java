package com.jxgyl.message.thymeleaf;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.Variable;

/**
 * 消息模板测试
 * 
 * @author iss002
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class TemplateTest {

	@Autowired
	private ThymeleafHelper thymeleafHelper;

	/**
	 * 纯文本
	 */
	@Test
	public void process_1() {
		String template = MessageTemplateEnum.PURE_TEXT.getTemplate();
		String result = thymeleafHelper.process(template, null);
		assertEquals(null, result);
	}

	/**
	 * 纯模板
	 */
	@Test
	public void process_2() {
		String template = MessageTemplateEnum.IMPORT_USER.getTemplate();
		String result = thymeleafHelper.process(template, null);
		System.out.println(result);
	}
	
	/**
	 * 模板和域值对
	 */
	@Test
	public void process_3() {
		String template = MessageTemplateEnum.RESET_PASSWORD.getTemplate();
		List<Variable> vars = new ArrayList<>(1);
		vars.add(Variable.createVar("username", "iss002"));
		vars.add(Variable.createVar("uuid", "uuid00100"));
		String result = thymeleafHelper.process(template, vars);
		System.out.println(result);
	}
	
}
