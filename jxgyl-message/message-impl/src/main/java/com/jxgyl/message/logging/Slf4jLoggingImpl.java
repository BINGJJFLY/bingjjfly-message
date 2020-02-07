package com.jxgyl.message.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jxgyl.message.Message;

/**
 * 
 * Slf4j组件
 * 
 * @author iss002
 *
 */
@Component("slf4jLoggingService")
public class Slf4jLoggingImpl implements LoggingService {

	private static final Logger LOGGING = LoggerFactory.getLogger(Slf4jLoggingImpl.class);

	@Autowired
	private PerpetualLoggingImpl delegate;

	@Override
	public void log(Message msg, boolean success) {
		delegate.log(msg, success);
		if (success) {
			LOGGING.info("【发送成功的消息内容】\r\n{}", msg);
		} else {
			LOGGING.error("【发送异常的消息内容】\r\n{}", msg);
		}
	}
}
