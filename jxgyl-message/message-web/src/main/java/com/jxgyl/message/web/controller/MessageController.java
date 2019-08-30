package com.jxgyl.message.web.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxgyl.message.Message;
import com.jxgyl.message.MessageSender;
import com.jxgyl.message.MessageTemplateEnum;
import com.jxgyl.message.Variable;
import com.jxgyl.message.queue.producer.MessageProducer;

@Controller
public class MessageController {

	@Autowired
	@Qualifier("emailSender")
	private MessageSender sender;
	@Autowired
	@Qualifier("messageRedisProducer")
	private MessageProducer producer;

	@ResponseBody
	@RequestMapping("/send")
	public void send(String[] to, String username, String uuid) {
		Message msg = Message.createEmail(null, to, null, null, Arrays.asList(Variable.createVar("username", username), Variable.createVar("uuid", uuid)), MessageTemplateEnum.RESET_PASSWORD);
		sender.send(msg);
	}

	@ResponseBody
	@RequestMapping("/produce")
	public void produce(String[] to, String username, String uuid) {
		Message email = Message.createEmail(null, to, null, null, Arrays.asList(Variable.createVar("username", username), Variable.createVar("uuid", uuid)), MessageTemplateEnum.RESET_PASSWORD);
		producer.produceEmail(email);
	}
	
	@ResponseBody
	@RequestMapping("/abs")
	public void abs(String[] to, int count) {
		String from = null;
		String subject = "测试条数";
		String text = null;
		Message[] msgs = new Message[count];
		for (int i = 0; i < count; i++) {
			msgs[i] = Message.createEmail(from, to, subject + "_" + i, text, Arrays.asList(Variable.createVar("username", "BINGJJFLY"), Variable.createVar("uuid", "wrenfas-fafdnsd-naqzdfp")), MessageTemplateEnum.RESET_PASSWORD);
		}
		producer.produceEmail(msgs);
	}
}
