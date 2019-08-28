package com.jxgyl.message.email;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxgyl.message.Message;
import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.Variable;

/**
 * Email发送测试
 *
 * @author iss002
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class EmailSenderTest {
	
	@Autowired
	private EmailSender emailSender;
	
	String from = "BINGJJFLY@163.com";
	String subject = "测试Email发送者";
	String text = "测试Email发送者";
	
	/**
	 * 纯文本 单用户 单消息
	 */
	@Test
	public void send_1() {
		try {
			String[] to = { "wangjz@shougangfund.cn" };
			emailSender.send(Message.createEmail(from, to, subject, text, null, MessageTemplateEnum.PURE_TEXT));
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 域值对 单用户 单消息
	 */
	@Test
	public void send_2() {
		String[] to = { "wangjz@shougangfund.cn" };
		emailSender.send(Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER));
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 域值对 多用户 单消息
	 */
	@Test
	public void send_3() {
		String[] to = { "wangjz@shougangfund.cn", "mr_zhen163@163.com", "1772370296@qq.com" };
		emailSender.send(Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER));
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 域值对 多用户 多消息
	 */
	@Test
	public void send_4() {
		String[] to = { "wangjz@shougangfund.cn", "mr_zhen163@163.com", "1772370296@qq.com" };
		Message[] msgs = new Message[3];
		for (int i = 0; i < 3; i++) {
			String txt = "【域值对 多用户 多消息】 ~ Index_" + i;
			msgs[i] = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", txt)), MessageTemplateEnum.IMPORT_USER);
		}
		emailSender.send(msgs);
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 域值对 单用户 单消息 from_null
	 */
	@Test
	public void send_5() {
		String from = null;
		String[] to = { "wangjz@shougangfund.cn" };
		emailSender.send(Message.createEmail(from, to, "测试客户端授权码", text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER));
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册发送邮件服务
	 */
	@Test
	public void send_6() {
		for(;;) {
			
		}
	}
}
