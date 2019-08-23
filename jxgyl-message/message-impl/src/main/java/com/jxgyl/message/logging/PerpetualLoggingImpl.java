package com.jxgyl.message.logging;

import org.springframework.stereotype.Component;

import com.jxgyl.message.Message;

/**
 * 持久化组件
 * 
 * @author iss002
 *
 */
@Component("perpetualLoggingService")
public class PerpetualLoggingImpl implements LoggingService {
	
	@Override
	public void log(Message msg, boolean success) {
		// TODO 日志入库
	}

}
