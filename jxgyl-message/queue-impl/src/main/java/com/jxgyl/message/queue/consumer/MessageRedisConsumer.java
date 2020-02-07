package com.jxgyl.message.queue.consumer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jxgyl.message.Message;
import com.jxgyl.message.queue.RedisMQ;

/**
 * Redis组件
 * 
 * @author iss002
 *
 */
@Service("messageRedisConsumer")
public class MessageRedisConsumer implements MessageConsumer {

	@Autowired
	protected RedisTemplate<Object, Message[]> redisTemplate;

	@Override
	public List<Message> consumeEmail() {
		// 这里可以使用阻塞队列，但是有个问题是如果线程一直阻塞在这里Redis的客户端连接会变成空闲连接，服务器会切断连接，此时会抛出异常
		// TimeUnit(时间单位），retryDelayTime(long型，失败后过多久的时间执行），retries(int型，重试次数)
		// millisecond，1000，3
		// 仍然无效：1、消息状态位置为失败走重发送机制；2、重发送机制仍失败，记录日志人工发送
		Message[] emails = null;
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		long retryDelayTime = 1000;
		int retries = 3;
		/*for (;retries > 0;retries--) {
			try {
				emails = redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).rightPop(0, TimeUnit.MILLISECONDS);
				break;
			} catch (Exception e) {
				try {
					timeUnit.sleep(retryDelayTime);
				} catch (InterruptedException e1) {
				}
			}
		}*/
		try {
			emails = redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).rightPop(0, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(new RedisConn(emails, executor, retries), 0, retryDelayTime, timeUnit);
		}
		// 应该添加日志记录消息已被取出
		// 这里还是有问题，消息被取出后由于网络问题或者是应用服务器问题，导致消息未被正常消费，消息丢失，使用RPOPLPUSH
		// 加入一个处理列表
//		redisTemplate.opsForList().rightPopAndLeftPush(RedisMQ.EMAIL_QUEUE, "temp_email_queue", 0, TimeUnit.MILLISECONDS);
		if (emails != null) {
			return Arrays.asList(emails);
		}
		return null;
	}
	
	private class RedisConn implements Runnable {
		Message[] emails;
		ScheduledExecutorService executor;
		int retries;
		public RedisConn(Message[] emails, ScheduledExecutorService executor, int retries) {
			this.emails = emails;
			this.executor = executor;
			this.retries = retries;
		}
		@Override
		public void run() {
			try {
				if (retries > 0) {
					emails = redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).rightPop(0, TimeUnit.MILLISECONDS);
				} else {
					executor.shutdown();
				}
			} catch (Exception e) {
				retries--;
			}
		}
	}

	@Override
	public List<Message> consumeText() {
		Message[] texts = redisTemplate.boundListOps(RedisMQ.TEXT_QUEUE).rightPop();
		if (texts != null) {
			return Arrays.asList(texts);
		}
		return null;
	}

}
