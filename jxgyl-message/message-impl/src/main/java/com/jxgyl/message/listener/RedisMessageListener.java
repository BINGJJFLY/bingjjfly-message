package com.jxgyl.message.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxgyl.message.Message;
import com.jxgyl.message.email.EmailSender;

@Service("redisMessageListener")
public class RedisMessageListener implements MessageListener {
	
	@Autowired
	private EmailSender emailSender;

	@Override
	public void onMessage(Message msg) {
		if (Message.MessageTypeEnum.EMAIL.name().equals(msg.getType().name())) {
			emailSender.send(msg);
		}
	}

}
