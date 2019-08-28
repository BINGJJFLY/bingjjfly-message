package com.jxgyl.message.listener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxgyl.message.Message;
import com.jxgyl.message.Message.MessageTypeEnum;
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
	public void onMessage(Message... msgs) {
		if (msgs != null) {
			email(msgs);
		}
	}

	private void email(Message[] msgs) {
		List<Message> emails = Arrays.stream(msgs).filter(msg -> MessageTypeEnum.EMAIL.equals(msg.getType()))
				.collect(Collectors.toList());
		emailSender.send(emails.toArray(new Message[] {}));
	}

}
