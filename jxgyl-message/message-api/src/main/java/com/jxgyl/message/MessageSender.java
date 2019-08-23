package com.jxgyl.message;

/**
 * 消息发送服务
 * <p>
 * 具体实现的组件：
 * </p>
 * <ul>
 * <li>Email组件</li>
 * <li>短信组件</li>
 * </ul>
 * 
 * @author iss002
 *
 */
public interface MessageSender {

	/**
	 * 发送信息
	 * 
	 * @param msgs
	 */
	void send(Message... msgs);

}
