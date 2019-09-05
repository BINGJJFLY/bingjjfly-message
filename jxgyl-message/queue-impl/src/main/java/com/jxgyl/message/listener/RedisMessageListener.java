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
	private MessageSender emailSender;

	@Override
	public void onMessage(Message msg) {
		if (msg != null) {
			email(msg);
		}
	}

	private void email(Message msg) {
		emailSender.send(msg);
	}

}
