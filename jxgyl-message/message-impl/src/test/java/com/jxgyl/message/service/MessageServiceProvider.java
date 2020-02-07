package com.jxgyl.message.service;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxgyl.message.Message;
import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.Variable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class MessageServiceProvider {
	
	@Autowired
	private MessageService messageService;

	@Test
	public void provideService() {
		for (;;) {

		}
	}
	
	@Test
	@Rollback(false)
	@Transactional
	public void batchInsert() {
		Message msg = Message.createEmail(null, new String[] {"hello@163.com"}, null, null, Arrays.asList(Variable.createVar("name", "iss002")), MessageTemplateEnum.RESET_PASSWORD);
		messageService.batchInsert(msg);
	}
	
}
