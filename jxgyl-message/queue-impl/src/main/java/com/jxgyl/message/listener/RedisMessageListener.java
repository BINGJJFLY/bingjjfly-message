package com.jxgyl.message.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxgyl.message.Message;
import com.jxgyl.message.MessageSender;

/**
 * Redis组件
 *
 * @author iss002
 *
 */
@Service("redisMessageListener")
public class RedisMessageListener implements MessageListener {

	@Resource(name = "emailSender")
	private MessageSender messageSender;

	@Override
	public void onMessage(Message msg) {
		switch (msg.getType()) {
		case EMAIL:
			messageSender.send(msg);
			break;
		case TEXT:
			// 发送短信
			break;
		default:
			break;
		}
	}

}
