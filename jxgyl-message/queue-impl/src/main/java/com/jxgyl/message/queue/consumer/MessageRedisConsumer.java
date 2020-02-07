package com.jxgyl.message.queue.consumer;

import java.util.Arrays;
import java.util.List;
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
		// 这里可以使用阻塞队列
		Message[] emails = redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).rightPop(0, TimeUnit.MILLISECONDS);
		// 应该添加日志记录消息已被取出
		// 这里还是有问题，消息被取出后由于网络问题或者是应用服务器问题，导致消息未被正常消费，消息丢失，使用RPOPLPUSH
		// 加入一个处理列表
//		redisTemplate.opsForList().rightPopAndLeftPush(RedisMQ.EMAIL_QUEUE, "temp_email_queue", 0, TimeUnit.MILLISECONDS);
		if (emails != null) {
			return Arrays.asList(emails);
		}
		return null;
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
