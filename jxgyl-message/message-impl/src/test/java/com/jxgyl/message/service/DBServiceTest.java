package com.jxgyl.message.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jxgyl.message.Attachment;
import com.jxgyl.message.Message;
import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.Variable;
import com.jxgyl.message.service.api.AttachmentDBService;
import com.jxgyl.message.service.api.MessageDBService;
import com.jxgyl.message.service.api.VariableDBService;
import com.jxgyl.message.service.converter.Attachment2DB;
import com.jxgyl.message.service.converter.Message2DB;
import com.jxgyl.message.service.converter.Message2DB.StatusEnum;
import com.jxgyl.message.service.converter.Variable2DB;
import com.jxgyl.message.service.domain.Attachment_DB;
import com.jxgyl.message.service.domain.Message_DB;

/**
 * 信息基本服务层测试
 *
 * @author iss002
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class DBServiceTest {

	@Autowired
	private MessageDBService messageDBService;
	@Autowired
	private AttachmentDBService attachmentDBService;
	@Autowired
	private VariableDBService variableDBService;

	String from = "BINGJJFLY@163.com";
	String[] to = { "wangjz@shougangfund.cn" };
	String subject = "测试Email发送者";
	String text = "测试Email发送者";

	@Test
	@Rollback(false)
	@Transactional
	public void insertMessage() {
		Message msg = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)),
				MessageTemplateEnum.IMPORT_USER);
		List<Message_DB> list = Message2DB.msg2DB(StatusEnum.SUCCESS, msg);
		messageDBService.batchInsert(list);
		list.forEach(db -> System.out.println(db.getId()));
	}

	@Test
	@Rollback(false)
	@Transactional
	public void insertMessages() {
		Message msg_1 = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)),
				MessageTemplateEnum.IMPORT_USER);
		Message msg_2 = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)),
				MessageTemplateEnum.IMPORT_USER);
		Message msg_3 = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)),
				MessageTemplateEnum.IMPORT_USER);
		List<Message_DB> list = Message2DB.msg2DB(StatusEnum.SUCCESS, msg_1, msg_2, msg_3);
		messageDBService.batchInsert(list);
		list.forEach(db -> System.out.println(db.getId()));
	}

	@Test
	@Rollback(false)
	@Transactional
	public void insertAttachment() {
		attachmentDBService.batchInsert(
				Attachment2DB.attach2DB(70, StatusEnum.SUCCESS, Attachment.createAttach("logo", "logo.png")));
	}

	@Test
	@Rollback(false)
	@Transactional
	public void insertAttachments() {
		List<Attachment_DB> dbs = Attachment2DB.attach2DB(70, StatusEnum.SUCCESS,
				Attachment.createAttach("logo", "logo.png"), Attachment.createAttach("hello", "world.png"),
				Attachment.createAttach("name", "static/images/iss002.png"));
		attachmentDBService.batchInsert(dbs);
	}

	@Test
	@Rollback(false)
	@Transactional
	public void insertVariable() {
		List<Variable> vars = new ArrayList<Variable>(1);
		vars.add(Variable.createVar("username", "iss002"));
		variableDBService.batchInsert(Variable2DB.var2DB(70, StatusEnum.SUCCESS, vars));
	}

	@Test
	@Rollback(false)
	@Transactional
	public void insertVariables() {
		List<Variable> vars = new ArrayList<Variable>(1);
		vars.add(Variable.createVar("username", "iss002"));
		vars.add(Variable.createVar("uuid", "raflwer===Djlwer/df"));
		vars.add(Variable.createVar("age", "18"));
		variableDBService.batchInsert(Variable2DB.var2DB(70, StatusEnum.SUCCESS, vars));
	}

	@Test
	@Rollback(false)
	@Transactional
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
		List<Message_DB> list = Message2DB.msg2DB(StatusEnum.SUCCESS, msg_1, msg_2, msg_3);
		messageDBService.batchInsert(list);
	}

	@Test
	@Rollback(false)
	@Transactional
	public void markAbnormal() {
		// List<String> list = Arrays.asList("4298e73b-44a3-4d4a-b6df-6f9f719fd456",
		// "7ed6f50b-19be-47b8-9ee5-1707971d2614",
		// "7a5eb911-3eb1-41f3-b99e-2cf82e00e2ad");
		List<Integer> list = Arrays.asList(1, 2, 3);
		messageDBService.markAbnormal(list);
	}

	@Test
	public void selectAbnormal() {
		List<Message_DB> dbs = messageDBService.selectAbnormal();
		System.out.println(dbs);
	}

}
