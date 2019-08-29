package com.jxgyl.message.queue.producer;

import java.util.Arrays;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.jxgyl.message.Message;
import com.jxgyl.message.listener.RedisMessageListener;
import com.jxgyl.message.queue.RedisMQ;
import com.jxgyl.message.queue.consumer.MessageRedisConsumer;
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
	private RedisMessageListener listener;
	@Autowired
	private MessageRedisConsumer consumer;
	@Autowired
	private MessageService messageService;

	@Override
	public void produceEmail(Message... emails) {
		if (emails != null && emails.length > 0) {
			executor.submit(new PushEmailTask(emails));
		}
	}

	@Override
	public void run() {
		executor.execute(new ReidsMQSession(listener, consumer));
	}

	@Override
	public void produceText(Message... texts) {
		if (texts != null) {
			redisTemplate.boundListOps(RedisMQ.TEXT_QUEUE).leftPush(texts);
		}
	}

	class PushEmailTask implements Callable<Long> {
		Message[] emails;

		PushEmailTask(Message[] emails) {
			this.emails = emails;
		}

		@Override
		public Long call() throws Exception {
			try {
				LOGGER.trace("【生产者生产Email信息准备存入数据库】\r\n{}", Arrays.toString(emails));
				messageService.batchInsert(emails);
				LOGGER.trace("【生产者生产Email信息准备存入Redis】\r\n{}", Arrays.toString(emails));
				Long count = redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).leftPush(emails);
				LOGGER.info("【Email信息存入Redis】\r\n{}", Arrays.toString(emails));
				if (count > 0) {
					run();
				}
			} catch (Exception e) {
				LOGGER.error("【生产者生产Email信息时异常】\r\n{}", Arrays.toString(emails), e);
				executor.execute(new MarkAbnormalTask(emails));
			}
			return null;
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
}
