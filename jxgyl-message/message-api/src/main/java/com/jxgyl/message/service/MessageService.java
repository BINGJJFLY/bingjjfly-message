package com.jxgyl.message.service;

import com.jxgyl.message.Message;

/**
 * 信息入库
 *
 * @author iss002
 *
 */
public interface MessageService {

	/**
	 * 入库
	 * 
	 * @param msgs
	 */
	void batchInsert(Message... msgs);
	
	/**
	 * 标记异常
	 * 
	 * @param msgs
	 */
	void markAbnormal(Message... msgs);

}
