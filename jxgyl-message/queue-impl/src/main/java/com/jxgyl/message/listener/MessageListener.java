package com.jxgyl.message.listener;

import com.jxgyl.message.Message;

/**
 * 消息监听器
 * <p>
 * 具体实现的组件：
 * </p>
 * <ul>
 * <li>Redis组件</li>
 * <li>ActiveMQ组件</li>
 * </ul>
 * 
 * @author iss002
 *
 */
public interface MessageListener {

	/**
	 * 处理消息
	 * 
	 * @param msg
	 */
	void onMessage(Message msg);

}
