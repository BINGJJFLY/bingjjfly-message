package com.jxgyl.message.service;

import java.util.Arrays;
import java.util.List;

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
import com.jxgyl.message.service.domain.Message_DB;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class DBServiceTest {
	
	@Autowired
	private MessageDBService messageDBService;
	
	String from = "BINGJJFLY@163.com";
	String[] to = { "wangjz@shougangfund.cn" };
	String subject = "测试Email发送者";
	String text = "测试Email发送者";

	@Test
	@Rollback(false)
	@Transactional
	public void insertMessage() {
		Message msg = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER);
		List<Message_DB> list = Message2DB.msg2DB(msg);
		messageDBService.batchInsert(list);
		list.forEach(db -> System.out.println(db.getId()));
	}
	
	@Test
	@Rollback(false)
	@Transactional
	public void insertMessages() {
		Message msg_1 = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER);
		Message msg_2 = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER);
		Message msg_3 = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER);
		List<Message_DB> list = Message2DB.msg2DB(msg_1, msg_2, msg_3);
		messageDBService.batchInsert(list);
		list.forEach(db -> System.out.println(db.getId()));
	}
	
}
