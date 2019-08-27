package com.jxgyl.message.queue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxgyl.message.Attachment;
import com.jxgyl.message.Message;
import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.Variable;
import com.jxgyl.message.queue.consumer.MessageRedisConsumer;
import com.jxgyl.message.queue.producer.MessageRedisProducer;

/**
 * RedisMQ方式：生产消费消息测试
 * 
 * @author iss002
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class RedisMQTest {

	@Autowired
	private MessageRedisProducer producer;
	@Autowired
	private MessageRedisConsumer consumer;

	String from = "BINGJJFLY@163.com";
	String[] to = { "wangjz@shougangfund.cn", "mr_zhen163@163.com", "1772370296@qq.com" };
	String subject = "测试Email生产者";
	String text = "RedisMQ方式测试Email生产者";

	@Test
	public void produceEmail() {
		String subject = "测试Email生产者和消费者（生产者生产后异步调用消费者）【多个接受邮箱】";
		String text = "RedisMQ方式测试Email生产者和消费者（生产者生产后异步调用消费者）";
		producer.produceEmail(Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER));
	}
	
	@Test
	public void produceEmails() {
		Message[] emails = new Message[5];
		for (int i = 0; i < 5; i++) {
			emails[i] = Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER);
		}
		producer.produceEmail(emails);
	}
	
	@Test
	public void consumeEmails() {
		for (;;) {
			List<Message> emails = consumer.consumeEmail();
			if (emails != null) {
				emails.forEach(email -> System.out.println(email));
			} else {
				break;
			}
		}
	}
	
	@Test
	public void produceAndConsumeEmail() {
		try {
			String[] to = { "wangjz@shougangfund.cn" };
			String subject = "测试Email生产者和消费者（生产者生产后异步调用消费者）【开启异步消费】";
			String text = "RedisMQ方式测试Email生产者和消费者（生产者生产后异步调用消费者）";
			producer.produceEmail(Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER));
			Thread.sleep(7000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void concurrentProduceAndConsumeEmail() {
		int threadCount = 3;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);
		for (int i = 0; i < threadCount; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName() + " is waitting.");
						cyclicBarrier.await();
						System.out.println(Thread.currentThread().getName() + " is working.");
						String[] to = { "wangjz@shougangfund.cn" };
						String subject = "生产者同步生产，消费者异步消费";
						String text = "生产者同步生产，消费者异步消费";
						producer.produceEmail(Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void attachmentEmail() {
		try {
			String[] to = { "wangjz@shougangfund.cn" };
			String subject = "测试发送带附件的Email";
			String text = "测试发送带附件的Email";
			producer.produceEmail(Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.IMPORT_USER, Attachment.createAttach("iss002.png", "iss002.png"), Attachment.createAttach("plugin.zip", "plugin.zip")));
			Thread.sleep(7000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void embeddedAttachmentEmail() {
		try {
			String from = null;
			String[] to = { "wangjz@shougangfund.cn" };
			String subject = null;
			String text = null;
			producer.produceEmail(Message.createEmail(from, to, subject, text, Arrays.asList(Variable.createVar("name", text)), MessageTemplateEnum.RESET_PASSWORD));
			Thread.sleep(7000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
