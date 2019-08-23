package com.jxgyl.message.queue.consumer;

import java.util.Arrays;
import java.util.List;

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
		Message[] emails = redisTemplate.boundListOps(RedisMQ.EMAIL_QUEUE).rightPop();
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
