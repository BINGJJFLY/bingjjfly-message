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

@Controller
public class MessageController {

	@Autowired
	@Qualifier("emailSender")
	private MessageSender sender;

	@ResponseBody
	@RequestMapping("/send")
	public void send() {
		Message msg = Message.createEmail(null, new String[] {"wangjz@shougangfund.cn"}, null, null, Arrays.asList(Variable.createVar("username", "测试生产"), Variable.createVar("uuid", "www.hhh.com")), MessageTemplateEnum.RESET_PASSWORD);
		sender.send(msg);
	}

}
