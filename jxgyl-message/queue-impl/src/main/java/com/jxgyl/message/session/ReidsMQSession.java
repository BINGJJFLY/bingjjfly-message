package com.jxgyl.message.session;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.jxgyl.message.Message;
import com.jxgyl.message.listener.RedisMessageListener;
import com.jxgyl.message.queue.consumer.MessageRedisConsumer;

/**
 * RedisMQ消息会话
 *
 * @author iss002
 *
 */
public class ReidsMQSession implements Session {

	private RedisMessageListener listener;
	private MessageRedisConsumer consumer;
	
	public ReidsMQSession(RedisMessageListener listener, MessageRedisConsumer consumer) {
		this.listener = listener;
		this.consumer = consumer;
	}

	@Override
	public void run() {
		for (List<Message> msgs; !CollectionUtils.isEmpty(msgs = consumer.consumeEmail());) {
			msgs.forEach(msg -> listener.onMessage(msg));
		}
	}

}
