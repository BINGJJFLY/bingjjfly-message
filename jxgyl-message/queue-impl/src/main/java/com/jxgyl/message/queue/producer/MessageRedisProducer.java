package com.jxgyl.message.queue.producer;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.jxgyl.message.Message;
import com.jxgyl.message.queue.RedisMQ;
import com.jxgyl.message.service.MessageService;
import com.jxgyl.message.session.ReidsMQSession;

/**
 * Redis组件
 * 
 * @author iss002
 *
 */
@Service("messageRedisProducer")
public class MessageRedisProducer implements MessageProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageRedisProducer.class);

	@Autowired
	private RedisTemplate<Object, Message[]> redisTemplate;
	@Autowired
	private ThreadPoolTaskExecutor executor;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ReidsMQSession redisMqSession;
	
	@Value("${message.producer.on}")
	private boolean on;

	@Override
	public void produceEmail(Message... emails) {
		if (emails != null && emails.length > 0) {
			if (on) {
				executor.execute(new PushEmailTask(emails));
			} else {
				executor.execute(new MarkErrorTask(emails));
			}
		}
	}

	@Override
	public Long emailQueueUp(Message... emails) {
		if (emails != null) {
			return redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).leftPush(emails);
		}
		return 0L;
	}

	@Override
	public void run() {
		redisMqSession.run();
	}

	@Override
	public void produceText(Message... texts) {
		if (texts != null) {
			redisTemplate.boundListOps(RedisMQ.TEXT_QUEUE).leftPush(texts);
		}
	}

	public class PushEmailTask implements Runnable {
		Message[] emails;

		PushEmailTask(Message[] emails) {
			this.emails = emails;
		}

		public Message[] getEmails() {
			return emails;
		}

		@Override
		public void run() {
			try {
				LOGGER.trace("【生产者生产Email信息准备存入数据库】\r\n{}", Arrays.toString(emails));
				messageService.batchInsert(emails);
				LOGGER.trace("【生产者生产Email信息准备存入Redis】\r\n{}", Arrays.toString(emails));
				Long count = emailQueueUp(emails);
				LOGGER.info("【Email信息存入Redis】\r\n{}", Arrays.toString(emails));
				if (count > 0) {
					MessageRedisProducer.this.run();
				}
			} catch (Exception e) {
				LOGGER.error("【生产者生产Email信息时异常】\r\n{}", Arrays.toString(emails), e);
				executor.execute(new MarkAbnormalTask(emails));
			}
		}
	}

	class StoreEmailTask implements Runnable {
		Message[] emails;

		StoreEmailTask(Message[] emails) {
			this.emails = emails;
		}

		@Override
		public void run() {
			messageService.batchInsert(emails);
		}
	}

	class MarkAbnormalTask implements Runnable {
		Message[] emails;

		public MarkAbnormalTask(Message[] emails) {
			this.emails = emails;
		}

		@Override
		public void run() {
			messageService.markAbnormal(emails);
		}
	}
	
	class MarkErrorTask implements Runnable {
		Message[] emails;

		public MarkErrorTask(Message[] emails) {
			this.emails = emails;
		}

		@Override
		public void run() {
			messageService.batchInsertError(emails);
			LOGGER.info("【开关设置为关闭消息不发送】\r\n" + Arrays.toString(emails));
		}
	}
}
