package com.jxgyl.message.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jxgyl.message.Message;
import com.jxgyl.message.service.MessageService;

/**
 * 持久化组件
 * 
 * @author iss002
 *
 */
@Component("perpetualLoggingService")
public class PerpetualLoggingImpl implements LoggingService {

	@Autowired
	private MessageService messageService;

	@Override
	public void log(Message msg, boolean success) {
		if (!success) {
			messageService.markAbnormal(msg);
		} else {
			messageService.markNormal(msg);
		}
	}
}
