package com.jxgyl.message.queue.producer;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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

	@Override
	public void produceEmail(Message... emails) {
		if (emails != null && emails.length > 0) {
			Future<Long> pushEmailFuture = executor.submit(new PushEmailTask(emails));
			try {
				Long count = pushEmailFuture.get();
				if (count > 0) {
					executor.execute(new ReidsMQSession(listener, consumer));
				}
			} catch (Exception e) {
				LOGGER.error("【生产者生产Email信息时异常】\r\n{}", Arrays.toString(emails), e);
			}
		}
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
			Long count = redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).leftPush(emails);
			LOGGER.info("【Email信息存入Redis】\r\n{}", Arrays.toString(emails));
			return count;
		}

	}
}
