package com.jxgyl.message.queue.consumer;

import java.util.List;

import com.jxgyl.message.Message;

/**
 * 消息消费者
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
public interface MessageConsumer {
	
	/**
	 * 将Email从消息队列中取出
	 * 
	 * @return
	 */
	List<Message> consumeEmail();
	
	/**
	 * 将短信从消息队列中取出
	 * 
	 * @return
	 */
	List<Message> consumeText();

}
