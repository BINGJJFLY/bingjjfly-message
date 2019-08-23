package com.jxgyl.message.service;

import java.util.List;

import com.jxgyl.message.service.domain.Message_DB;

/**
 * 消息入库
 *
 * @author iss002
 *
 */
public interface MessageDBService {
	
	/**
	 * 入库
	 * 
	 * @param msgs
	 */
	void batchInsert(List<Message_DB> msgs);
}
