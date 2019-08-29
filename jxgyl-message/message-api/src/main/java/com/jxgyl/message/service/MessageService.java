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
	 * 正常消息入库
	 * 
	 * @param msgs
	 */
	void batchInsert(Message... msgs);
	
	/**
	 * 异常消息入库
	 * 
	 * @param msgs
	 */
	void batchInsertAbnormal(Message... msgs);
	
	/**
	 * 标记异常
	 * 
	 * @param msgs
	 */
	void markAbnormal(Message... msgs);
	
	/**
	 * 标记正常
	 * 
	 * @param msgs
	 */
	void markNormal(Message... msgs);

	/**
	 * 查询异常信息
	 * 
	 * @return
	 */
	Message[] selectAbnormal();
}
