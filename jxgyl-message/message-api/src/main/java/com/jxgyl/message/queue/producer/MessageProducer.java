package com.jxgyl.message.queue.producer;

import com.jxgyl.message.Message;

/**
 * 消息生产者
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
public interface MessageProducer {

	/**
	 * 将Email放入消息队列中
	 * 
	 * @param emails
	 */
	void produceEmail(Message... emails);
	
	/**
	 * 将短信放入消息队列中
	 * 
	 * @param texts
	 */
	void produceText(Message... texts);

}
