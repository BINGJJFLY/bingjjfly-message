package com.jxgyl.message.arq;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jxgyl.message.Message;
import com.jxgyl.message.MessageSender;
import com.jxgyl.message.queue.producer.MessageProducer;
import com.jxgyl.message.service.MessageService;

/**
 * Email信息检错重发任务
 *
 * @author iss002
 *
 */
@Component
public class ResendEmailTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResendEmailTask.class);

	@Autowired
	private MessageService messageService;
	@Resource(name = "messageRedisProducer")
	private MessageProducer producer;
	@Resource(name = "emailSender")
	private MessageSender emailSender;

	public void resend() {
		LOGGER.info("【检错重发任务开始执行】");
		producer.emailQueueUp(bd());
		redis();
	}

	private Message[] bd() {
		Message[] emails = null;
		try {
			emails = messageService.selectAbnormal();
		} catch (Exception e) {
			LOGGER.error("【执行Email信息检错重发任务，读取数据库内信息时异常】", e);
		}
		return emails;
	}

	private void redis() {
		try {
			producer.run();
		} catch (Exception e) {
			LOGGER.error("【执行Email信息检错重发任务，读取Redis内信息时异常】", e);
		}
	}

}
