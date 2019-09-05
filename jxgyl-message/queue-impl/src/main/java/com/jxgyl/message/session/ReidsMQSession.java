package com.jxgyl.message.session;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component("reidsMQSession")
public class ReidsMQSession implements Session {

	@Autowired
	private RedisMessageListener listener;
	@Autowired
	private MessageRedisConsumer consumer;

	private ReentrantLock lock = new ReentrantLock();

	@Override
	public void run() {
		lock.lock();
		try {
			for (List<Message> msgs; !CollectionUtils.isEmpty(msgs = consumer.consumeEmail());) {
				if (!CollectionUtils.isEmpty(msgs)) {
					msgs.forEach(msg -> {
						listener.onMessage(msg);
						try {
							TimeUnit.MILLISECONDS.sleep(2000);
						} catch (Exception e) {
							//
						}
					});
				}
			}
		} finally {
			lock.unlock();
		}
	}

}
