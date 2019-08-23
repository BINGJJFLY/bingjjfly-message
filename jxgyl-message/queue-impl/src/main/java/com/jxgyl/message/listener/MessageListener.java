package com.jxgyl.message.listener;

import com.jxgyl.message.Message;

/**
 * 消息监听器
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
