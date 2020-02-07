package com.jxgyl.message.service;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxgyl.message.Attachment;
import com.jxgyl.message.Message;
import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.Variable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class MessageServiceTest {

	@Autowired
	private MessageService messageService;

	@Test
	public void batchInsert() {
		String from = null;
		String[] to = { "wangjz@shougangfund.cn", "iss002@shougangfund.cn", "bing@shougangfund.cn" };
		String subject = null;
		String text = null;
		Message msg_1 = Message.createEmail(from, to, subject, text,
				Arrays.asList(Variable.createVar("name", "iss002"), Variable.createVar("age", "18")),
				MessageTemplateEnum.IMPORT_USER, Attachment.createAttach("hello", "static/images/world.png"),
				Attachment.createAttach("logo", "static/images/logo.png"));
		Message msg_2 = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("man", "no")),
				MessageTemplateEnum.IMPORT_USER, Attachment.createAttach("man", "static/images/man.png"));
		Message msg_3 = Message.createEmail(from, to, subject, text,
				Arrays.asList(Variable.createVar("name", "bing"), Variable.createVar("var", "int"),
						Variable.createVar("string", "toString")),
				MessageTemplateEnum.IMPORT_USER, Attachment.createAttach("a", "static/images/a.png"),
				Attachment.createAttach("b", "static/images/b.png"),
				Attachment.createAttach("c", "static/images/c.png"));
		messageService.batchInsert(msg_1, msg_2, msg_3);
	}
}
