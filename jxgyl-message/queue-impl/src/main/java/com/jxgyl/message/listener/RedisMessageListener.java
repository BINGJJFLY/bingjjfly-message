package com.jxgyl.message.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jxgyl.message.Message;
import com.jxgyl.message.MessageSender;

@Service("redisMessageListener")
public class RedisMessageListener implements MessageListener {
	
	@Autowired
	@Qualifier("emailSender")
	private MessageSender messageSender;

	@Override
	public void onMessage(Message msg) {
		if (Message.MessageTypeEnum.EMAIL.name().equals(msg.getType().name())) {
			messageSender.send(msg);
		}
	}

}
