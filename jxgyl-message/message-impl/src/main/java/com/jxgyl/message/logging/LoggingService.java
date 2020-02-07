package com.jxgyl.message.logging;

import com.jxgyl.message.Message;

/**
 * 日志服务
 * <p>
 * 具体实现的组件：
 * </p>
 * <ul>
 * <li>Slf4j组件</li>
 * <li>持久化组件</li>
 * </ul>
 * 
 * @author iss002
 *
 */
public interface LoggingService {

	void log(Message msg, boolean success);
}
